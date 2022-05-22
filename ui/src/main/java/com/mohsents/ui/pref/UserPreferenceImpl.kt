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

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import com.mohsents.acc.util.DEFAULT_CURRENT
import com.mohsents.acc.util.DEFAULT_START_CHARGING
import com.mohsents.acc.util.DEFAULT_STOP_CHARGING
import com.mohsents.acc.util.DEFAULT_VOLT
import com.mohsents.shared.pref.DataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class UserPreferenceImpl @Inject constructor(
    private val dataStore: DataStore,
) : UserPreference {

    override suspend fun getUiPreference(): UiPreference {
        return UiPreference(
            service = get(SERVICE_KEY, false),
            limitChargingPower = get(LIMIT_CHARGING_POWER_KEY, false),
            chargingPower = retrieveChargingPower(),
            startStopCharring = retrieveStartStopCharging()
        )
    }

    private suspend fun retrieveStartStopCharging(): StartStopCharring {
        return StartStopCharring(
            checked = get(START_STOP_CHARGING_KEY, false),
            start = get(START_CHARGING, DEFAULT_START_CHARGING),
            stop = get(STOP_CHARGING, DEFAULT_STOP_CHARGING),
        )
    }

    private suspend fun retrieveChargingPower(): ChargingPower {
        return ChargingPower(
            checked = get(CHARGING_POWER_KEY, false),
            voltage = get(CHARGING_VOLTAGE_KEY, DEFAULT_VOLT),
            current = get(CHARGING_CURRENT_KEY, DEFAULT_CURRENT)
        )
    }

    override suspend fun <K : Preferences.Key<V>, V> save(
        key: K,
        value: V,
    ): Result<Unit> = dataStore.save(key, value)

    override suspend fun <K : Preferences.Key<V>, V> get(
        key: K,
        defaultValue: V,
    ): V {
        return dataStore.get(key, defaultValue).getOrDefault(flowOf(defaultValue)).first()
    }

    companion object Keys {
        val SERVICE_KEY = booleanPreferencesKey("service")
        val LIMIT_CHARGING_POWER_KEY = booleanPreferencesKey("limit_charging_power")

        val CHARGING_POWER_KEY = booleanPreferencesKey("charging_power")
        val CHARGING_VOLTAGE_KEY = intPreferencesKey("charging_voltage")
        val CHARGING_CURRENT_KEY = intPreferencesKey("charging_current")

        val START_STOP_CHARGING_KEY = booleanPreferencesKey("start_stop_charging")
        val START_CHARGING = intPreferencesKey("start_charging")
        val STOP_CHARGING = intPreferencesKey("stop_charging")
    }
}