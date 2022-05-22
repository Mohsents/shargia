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

package com.mohsents.ui.pref

import com.mohsents.acc.util.DEFAULT_CURRENT
import com.mohsents.acc.util.DEFAULT_START_CHARGING
import com.mohsents.acc.util.DEFAULT_STOP_CHARGING
import com.mohsents.acc.util.DEFAULT_VOLT

data class UiPreference(
    val service: Boolean = false,
    val limitChargingPower: Boolean = false,
    val chargingPower: ChargingPower = ChargingPower(),
    val startStopCharring: StartStopCharring = StartStopCharring()
)

data class ChargingPower(
    val checked: Boolean = false,
    val voltage: Int = DEFAULT_VOLT,
    val current: Int = DEFAULT_CURRENT,
)

data class StartStopCharring(
    val checked: Boolean = false,
    val start: Int = DEFAULT_START_CHARGING,
    val stop: Int = DEFAULT_STOP_CHARGING,
)