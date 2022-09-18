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

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mohsents.acc.util.currentRange
import com.mohsents.acc.util.voltRange
import com.mohsents.ui.R
import com.mohsents.ui.pref.UiPreference
import com.mohsents.ui.utils.VerticalSpace
import com.mohsents.ui.utils.length
import com.mohsents.ui.utils.localize

@Composable
fun PreferenceScreen(
    uiPreference: UiPreference,
    onServiceEnable: (Boolean) -> Unit,
    onLimitChargingPowerEnabled: (Boolean) -> Unit,
    onChargingPowerEnabled: (Boolean) -> Unit,
    chargingVoltage: (Int) -> Unit,
    chargingCurrent: (Int) -> Unit,
    onStartStopChargingEnabled: (Boolean) -> Unit,
    startCharging: (Int) -> Unit,
    stopCharging: (Int) -> Unit,
) {
    val (
        service,
        limitChargingPower,
        chargingPower,
        startStopCharring,
    ) = uiPreference

    CategorySwitchPreference(
        title = stringResource(id = R.string.enable_service_text_label),
        checked = service,
        onCheckedChange = onServiceEnable
    )

    VerticalSpace(value = 10.dp)

    SwitchPreference(
        title = stringResource(id = R.string.enable_limit_charging_power_text_label),
        summary = stringResource(id = R.string.enable_limit_charging_power_summary_label),
        checked = limitChargingPower,
        enabled = service && !chargingPower.checked,
        onCheckedChange = onLimitChargingPowerEnabled
    )

    SwitchPreference(
        title = stringResource(id = R.string.enable_set_charging_power_text_label),
        checked = chargingPower.checked,
        enabled = service && !limitChargingPower,
        onCheckedChange = onChargingPowerEnabled,
        content = {
            EditTextPreference(
                title = stringResource(id = R.string.setting_set_charging_voltage_text_label),
                summary = stringResource(id = R.string.setting_set_charging_voltage_summary_label,
                    chargingPower.voltage.localize()),
                enabled = service && chargingPower.checked,
                maxLength = voltRange.last.length,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = { text -> text.toInt() !in voltRange },
                errorText = stringResource(id = R.string.setting_set_charging_voltage_error_label),
                onEditSummery = { newVoltage -> chargingVoltage(newVoltage.toInt()) })

            EditTextPreference(
                title = stringResource(id = R.string.setting_set_charging_current_text_label),
                summary = stringResource(id = R.string.setting_set_charging_current_summary_label,
                    chargingPower.current.localize()),
                enabled = service && chargingPower.checked,
                maxLength = currentRange.last.length,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = { text -> text.toInt() !in currentRange },
                errorText = stringResource(id = R.string.setting_set_charging_current_error_label),
                onEditSummery = { newCurrent -> chargingCurrent(newCurrent.toInt()) })
        })

    SwitchPreference(
        title = stringResource(id = R.string.enable_set_start_stop_charging_text_label),
        checked = startStopCharring.checked,
        enabled = service,
        onCheckedChange = onStartStopChargingEnabled,
        content = {
            NumberPickerPreference(
                title = stringResource(id = R.string.setting_set_start_charging_text_label),
                summary = stringResource(id = R.string.setting_set_start_charging_summary_label,
                    startStopCharring.start.localize()),
                enabled = service && startStopCharring.checked,
                minValue = 0,
                maxValue = 100,
                defaultValue = startStopCharring.start,
                onSelectValue = { startAt -> startCharging(startAt) }
            )

            NumberPickerPreference(
                title = stringResource(id = R.string.setting_set_stop_charging_text_label),
                summary = stringResource(id = R.string.setting_set_stop_charging_summary_label,
                    startStopCharring.stop.localize()),
                enabled = service && startStopCharring.checked,
                minValue = 0,
                maxValue = 100,
                defaultValue = startStopCharring.stop,
                onSelectValue = { stopAt -> stopCharging(stopAt) }
            )
        })
}