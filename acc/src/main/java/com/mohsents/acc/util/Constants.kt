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

package com.mohsents.acc.util

const val ACC_PATH = "/dev/.vr25/acc"
const val ACCA = "$ACC_PATH/acca"

const val START_ACC = "daemon start"
const val STOP_ACC = "daemon stop"
const val DAEMON_STATUS = "daemon"

const val ACC_VERSION = "version"

const val BATTERY_INFO = "info"

const val SET = "set"
const val CURRENT_VOLTAGE = "voltage"
const val CURRENT_AMPERE = "current"

const val RESUME_CAPACITY = "resume_capacity"
const val PAUSE_CAPACITY = "pause_capacity"

val voltRange = IntRange(3700, 4300)
val currentRange = IntRange(0, 9999)

const val DEFAULT_VOLT = 3920
const val DEFAULT_CURRENT = 500

const val DEFAULT_BATTERY_INFO_VALUE = "N/A"
const val CAPACITY_PATTERN = """^\s*CAPACITY=(\d+)"""
const val UNKNOWN = "Unknown"
const val NOT_CHARGING = "Not charging"
const val DISCHARGING = "Discharging"
const val CHARGING = "Charging"
const val STATUS_PATTERN =
    """^\s*STATUS=(${CHARGING}|${DISCHARGING}|${NOT_CHARGING})"""
const val TEMP_PATTERN = """^\s*TEMP=(\d+)"""
const val CURRENT_NOW_PATTERN = """^\s*CURRENT_NOW=([+-]?([0-9]*[.])?[0-9]+)"""
const val VOLTAGE_NOW_PATTERN = """^\s*VOLTAGE_NOW=([+-]?([0-9]*[.])?[0-9]+)"""
const val POWER_NOW_PATTERN = """^\s*POWER_NOW=([+-]?([0-9]*[.])?[0-9]+)"""
const val HEALTH_PATTERN = """^\s*HEALTH=([a-zA-Z]+)"""