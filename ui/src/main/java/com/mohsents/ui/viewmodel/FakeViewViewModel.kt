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
import com.mohsents.ui.pref.UiPreference
import com.mohsents.ui.screen.ScreenState
import com.mohsents.ui.screen.UiState
import com.mohsents.ui.utils.fakeBatteryInfo

/**
 * Fake implementation of [MainViewModel] that used in compose previews and ui tests.
 */
object FakeViewViewModel : MainViewModel {

    private var _uiState: MutableState<UiState> =
        mutableStateOf(UiState(batteryInfo = fakeBatteryInfo))

    override val uiState: State<UiState> = _uiState

    fun updateUiState(uiState: UiState) {
        _uiState.value = uiState
    }

    override suspend fun updateUiState() {}

    override val uiPreferenceState: State<UiPreference> = mutableStateOf(UiPreference())

    override suspend fun updateUiPreferenceState() {}

    override fun enableService(enable: Boolean) {}

    override fun enableLimitChargingPower(enable: Boolean) {}

    override fun enableChargingPower(enable: Boolean) {}

    override fun setChargingVoltage(voltage: Int) {}

    override fun setChargingCurrent(current: Int) {}

    override fun enableStartStopCharging(enable: Boolean) {}

    override fun setStartCharging(startAt: Int) {}

    override fun setStopCharging(stopAt: Int) {}

    override suspend fun getScreenState(): ScreenState = ScreenState.MAIN_SCREEN
}