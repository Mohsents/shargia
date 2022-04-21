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

package com.mohsents.ui.screen

import com.mohsents.acc.model.BatteryInfo
import com.mohsents.ui.defaultBatteryInfo

/**
 * A wrapper for [ScreenState.MAIN_SCREEN]'s UI state.
 *
 * It contains loading, error and [BatteryInfo] states to display in the UI.
 *
 * @param isLodging while data loading it become true otherwise false
 * @param isError if an error propagate it become true otherwise false
 * @param batteryInfo data to display in UI which is [BatteryInfo]
 */
data class UiState(
    val isLodging: Boolean = true,
    val isError: Boolean = false,
    val batteryInfo: BatteryInfo = defaultBatteryInfo
)