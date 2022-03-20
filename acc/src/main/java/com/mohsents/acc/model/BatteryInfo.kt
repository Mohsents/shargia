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

package com.mohsents.acc.model

data class BatteryInfo(
    val capacity: String, // Battery level, 0-100
    val status: String, // Charging, Discharging or Idle
    val temp: String, // Always in (ºC * 10)
    val currentNow: String, // Charging current (Amps)
    val voltageNow: String, // Charging voltage (Volts)
    val powerNow: String, // (currentNow * voltageNow) (Watts)
)