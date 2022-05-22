/*
 * Copyright 2022 Mohsents
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mohsents.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsents.acc.Acc
import com.mohsents.acc.util.CHARGING
import com.mohsents.shell.root.RootHandler
import com.mohsents.ui.pref.UiPreference
import com.mohsents.ui.pref.UserPreference
import com.mohsents.ui.pref.UserPreferenceImpl
import com.mohsents.ui.screen.ScreenState
import com.mohsents.ui.screen.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val acc: Acc,
    private val userPreference: UserPreference
) : ViewModel(), MainViewModel {

    private val _uiState: MutableState<UiState> = mutableStateOf(UiState())
    override val uiState: State<UiState> = _uiState

    private val _uiPreferenceState: MutableState<UiPreference> = mutableStateOf(UiPreference())
    override val uiPreferenceState: State<UiPreference> = _uiPreferenceState

    init {
        viewModelScope.launch {
            updateUiPreferenceState()
        }
    }

    override suspend fun updateUiState() {
        while (true) {
            delay(DELAY_UPDATE_BATTERY_INFO)
            acc.getBatteryInfo().onSuccess { batteryInfo ->
                _uiState.value =
                    UiState(isLodging = false, isError = false, batteryInfo = batteryInfo)
            }.onFailure {
                _uiState.value = UiState(isLodging = false, isError = true)
                return
            }
        }
    }

    override suspend fun getScreenState(): ScreenState = when {
        !RootHandler.isShellRooted() -> ScreenState.NO_ROOT
        acc.init().isFailure -> ScreenState.INITIALIZATION_FAILED
        else -> ScreenState.MAIN_SCREEN
    }

    override fun enableService(enable: Boolean) {
        updatePreference(UserPreferenceImpl.SERVICE_KEY, enable)

        viewModelScope.launch {
            val isDeviceCharging: Boolean = uiState.value.batteryInfo.status == CHARGING
            if (enable && isDeviceCharging) {
                acc.startDaemon()
            } else {
                acc.stopDaemon()
            }
        }
    }

    override fun enableLimitChargingPower(enable: Boolean) {
        updatePreference(UserPreferenceImpl.LIMIT_CHARGING_POWER_KEY, enable)
        viewModelScope.launch {
            if (enable) {
                acc.limitChargingPower()
            } else {
                acc.restoreChargingPower()
            }
        }
    }

    override fun enableChargingPower(enable: Boolean) {
        updatePreference(UserPreferenceImpl.CHARGING_POWER_KEY, enable)
        viewModelScope.launch {
            if (enable) {
                val voltage = uiPreferenceState.value.chargingPower.voltage
                val current = uiPreferenceState.value.chargingPower.current
                acc.setChargingVoltage(voltage)
                acc.setChargingCurrent(current)
            } else {
                acc.restoreChargingPower()
            }
        }
    }

    override fun setChargingVoltage(voltage: Int) {
        updatePreference(UserPreferenceImpl.CHARGING_VOLTAGE_KEY, voltage)
        viewModelScope.launch {
            acc.setChargingVoltage(voltage)
        }
    }

    override fun setChargingCurrent(current: Int) {
        updatePreference(UserPreferenceImpl.CHARGING_CURRENT_KEY, current)
        viewModelScope.launch {
            acc.setChargingCurrent(current)
        }
    }

    override fun enableStartStopCharging(enable: Boolean) {
        updatePreference(UserPreferenceImpl.START_STOP_CHARGING_KEY, enable)
        viewModelScope.launch {
            if (enable) {
                val startAt = uiPreferenceState.value.startStopCharring.start
                val stopAt = uiPreferenceState.value.startStopCharring.stop
                acc.setStartCharging(startAt)
                acc.setStopCharging(stopAt)
            } else {
                acc.restoreStartStopCharging()
            }
        }
    }

    override fun setStartCharging(startAt: Int) {
        updatePreference(UserPreferenceImpl.START_CHARGING, startAt)
        viewModelScope.launch {
            acc.setStartCharging(startAt)
        }
    }

    override fun setStopCharging(stopAt: Int) {
        updatePreference(UserPreferenceImpl.STOP_CHARGING, stopAt)
        viewModelScope.launch {
            acc.setStopCharging(stopAt)
        }
    }

    override suspend fun updateUiPreferenceState() {
        _uiPreferenceState.value = userPreference.getUiPreference()
    }

    private fun <K : Preferences.Key<V>, V> updatePreference(key: K, value: V) {
        viewModelScope.launch {
            userPreference.save(key, value)
            updateUiPreferenceState()
        }
    }

    companion object {
        private const val DELAY_UPDATE_BATTERY_INFO = 2000L
    }
}