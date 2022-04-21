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

package com.mohsents.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import com.mohsents.acc.model.BatteryInfo
import com.mohsents.acc.util.*

fun isInLandscape(context: Context): Boolean {
    val configuration = context.resources.configuration
    return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}

val defaultBatteryInfo = BatteryInfo(
    DEFAULT_BATTERY_INFO_VALUE,
    DEFAULT_BATTERY_INFO_VALUE,
    DEFAULT_BATTERY_INFO_VALUE,
    DEFAULT_BATTERY_INFO_VALUE,
    DEFAULT_BATTERY_INFO_VALUE,
    DEFAULT_BATTERY_INFO_VALUE,
    DEFAULT_BATTERY_INFO_VALUE
)

var fakeBatteryInfo = BatteryInfo(
    "40",
    "Charging",
    "35",
    "0.9",
    "5.00",
    "2",
    "Good"
)

fun BatteryInfo.localize(context: Context): BatteryInfo {
    val configuration = context.resources.configuration

    @Suppress("DEPRECATION")
    val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        configuration.locales[0]
    } else {
        configuration.locale
    }
    return if (locale.language == "fa") {
        BatteryInfo(
            status = localizeStatusToPersian(context, status),
            capacity = capacity.toPersianDigits(),
            temp = "°" + localizeTemp(context, temp).toPersianDigits(),
            currentNow = "${reorderMinusSign(currentNow).toPersianDigits()} ${context.getString(R.string.battery_info_current_suffix_label)}",
            voltageNow = "${voltageNow.toPersianDigits()} ${context.getString(R.string.battery_info_voltage_suffix_label)}",
            powerNow = "${reorderMinusSign(powerNow).toPersianDigits()} ${context.getString(R.string.battery_info_power_suffix_label)}",
            health = localizeHealthToPersian(context, health)
        )
    } else copy(
        temp = localizeTemp(context, temp),
        currentNow = "$currentNow ${context.getString(R.string.battery_info_current_suffix_label)}",
        voltageNow = "$voltageNow ${context.getString(R.string.battery_info_voltage_suffix_label)}",
        powerNow = "$powerNow ${context.getString(R.string.battery_info_power_suffix_label)}",
    )
}

private fun localizeStatusToPersian(context: Context, status: String) = when (status) {
    CHARGING -> context.getString(R.string.battery_info_charging_status_label)
    NOT_CHARGING, DISCHARGING -> context.getString(R.string.battery_info_discharge_status_label)
    UNKNOWN -> context.getString(R.string.battery_info_unknown_status_label)
    else -> context.getString(R.string.battery_info_unknown_status_label)
}

private fun localizeHealthToPersian(context: Context, health: String) = when (health) {
    GOOD_BATTERY -> context.getString(R.string.battery_info_good_battery_health_label)
    FAILED_BATTERY -> context.getString(R.string.battery_info_unknown_battery_health_label)
    DEAD_BATTERY -> context.getString(R.string.battery_info_dead_battery_health_label)
    OVER_VOLTAGE_BATTERY -> context.getString(R.string.battery_info_over_voltage_battery_health_label)
    OVER_HEATED_BATTERY -> context.getString(R.string.battery_info_over_heat_battery_health_label)
    else -> context.getString(R.string.battery_info_unknown_battery_health_label)
}

private fun localizeTemp(context: Context, temp: String): String {
    return if (temp.isNotEmpty() && temp != DEFAULT_BATTERY_INFO_VALUE) {
        "${temp.toInt().div(10)}${context.getString(R.string.battery_info_temp_suffix_label)}"
    } else {
        context.getString(R.string.battery_info_unknown_capacity_label)
    }
}

private fun reorderMinusSign(text: String): String {
    if (text.contains('-')) {
        return text.replace("-", "") + " -"
    }
    return text
}

/**
 * @author aminography
 */
fun String.toPersianDigits(): String = StringBuilder().also { builder ->
    val persianDigits = charArrayOf(
        '0' + 1728,
        '1' + 1728,
        '2' + 1728,
        '3' + 1728,
        '4' + 1728,
        '5' + 1728,
        '6' + 1728,
        '7' + 1728,
        '8' + 1728,
        '9' + 1728
    )
    toCharArray().forEach {
        builder.append(
            when {
                Character.isDigit(it) -> persianDigits["$it".toInt()]
                it == '.' -> "/"
                else -> it
            }
        )
    }
}.toString()