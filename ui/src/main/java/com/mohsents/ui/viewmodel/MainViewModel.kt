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

import androidx.compose.runtime.State
import com.mohsents.ui.pref.UiPreference
import com.mohsents.ui.screen.ScreenState
import com.mohsents.ui.screen.UiState

interface MainViewModel {
    suspend fun getScreenState(): ScreenState
    val uiState: State<UiState>
    suspend fun updateUiState()
    val uiPreferenceState: State<UiPreference>
    suspend fun updateUiPreferenceState()
    fun enableService(enable: Boolean)
    fun enableLimitChargingPower(enable: Boolean)
    fun enableChargingPower(enable: Boolean)
    fun setChargingVoltage(voltage: Int)
    fun setChargingCurrent(current: Int)
    fun enableStartStopCharging(enable: Boolean)
    fun setStartCharging(startAt: Int)
    fun setStopCharging(stopAt: Int)
}