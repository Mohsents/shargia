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

package com.mohsents.acc

import com.mohsents.acc.model.BatteryInfo

/**
 * Base interface for Advanced Charging Controller (ACC).
 */
interface Acc {
    /**
     * Execute shell commands for the Acc.
     * It execute one command at a time with [options]s and if successfully executed (Result code == 0), return sorted [String] from the Stdout, otherwise
     * it throw an [Exception] as follow based on the Result code:
     *
     * 1. False or general failure
     * 2. Incorrect command syntax
     * 3. Missing busybox binary
     * 4. Not running as root
     * 5. Update available ("--upgrade")
     * 6. No update available ("--upgrade")
     * 7. Failed to disable charging
     * 8. Daemon already running ("--daemon start")
     * 9. Daemon not running ("--daemon" and "--daemon stop")
     * 10. All charging switches fail (--test)
     * 11. Current (mA) out of 0-9999 range
     * 12. Initialization failed
     * 13. Failed to lock /dev/.vr25/acc/acc.lock
     * 14. ACC won't initialize, because the Magisk module disable flag is set
     * 15. Idle mode is supported (--test)
     *
     * Note that some of this result codes may ignore and as a result not thrown the corresponding [Exception].
     *
     * @param options Acc's command options to execute.
     * @return Sorted [String] from Stdout.
     *
     * @see com.mohsents.shell.command.Commander.execSu
     */
    suspend fun exec(vararg options: String): String

    /**
     * Initializes the Acc.
     * Note that DON'T start the Acc's daemon here.
     *
     * @return [Result] of [Unit]
     */
    suspend fun init(): Result<Unit>

    /**
     * Starts the Acc's daemon.
     *
     * @throws [AccException.DaemonExistsException] if the Acc's daemon already existed.
     * @return [Result] of [Unit]
     */
    suspend fun startDaemon(): Result<Unit>

    /**
     * Stop the Acc's daemon.
     *
     * @throws [AccException.DaemonNotExistsException] if the Acc's daemon not exist.
     * @return [Result] of [Unit]
     */
    suspend fun stopDaemon(): Result<Unit>

    /**
     * Returns the Acc's daemon state based on Result code.
     *
     * @return [Result] of [DaemonState.STARTED] if daemon already running and [Result] of [DaemonState.STOPPED] otherwise.
     */
    suspend fun getDaemonState(): Result<DaemonState>

    /**
     * Gets the battery info from Acc.
     *
     * Note that these info comes from kernel and may vary in other devices.
     * So we only focus on these:
     *
     * capacity
     * status
     * temp
     * current
     * voltage
     * power
     *
     * @return [Result] of [BatteryInfo]
     * @see BatteryInfo
     */
    suspend fun getBatteryInfo(): Result<BatteryInfo>

    /**
     * Sets the start capacity for charging.
     *
     * @param startAt charging starts at this level
     * @return [Result] of [Unit]
     */
    suspend fun setStartCharging(startAt: Int): Result<Unit>

    /**
     * Sets the pause capacity for charging.
     *
     * @param stopAt charging stops at this level
     * @return [Result] of [Unit]
     */
    suspend fun setStopCharging(stopAt: Int): Result<Unit>

    /**
     * Sets the voltage for charging.
     *
     * The values must be in specific range as follow:
     * [milliVolts]: 3700_4300
     *
     * @param milliVolts voltage current for charging
     * @return [Result] of [Unit]
     */
    suspend fun setChargingVoltage(milliVolts: Int): Result<Unit>

    /**
     * Sets the ampere current for charging.
     *
     * The values must be in specific range as follow:
     * [milliAmps]: 0_9999
     *
     * @param milliAmps current for charging
     * @return [Result] of [Unit]
     */
    suspend fun setChargingCurrent(milliAmps: Int): Result<Unit>

    /**
     * Sets an default current and voltage for limit charging to prolong battery life.
     *
     * @return [Result] of [Unit]
     */
    suspend fun limitChargingPower(): Result<Unit>

    /**
     * Sets the default maximum voltage and maximum current.
     *
     * @return [Result] of [Unit]
     */
    suspend fun restoreChargingPower(): Result<Unit>

    /**
     * Sets the default maximum percent (100) and minimum percent (0) for charging.
     *
     * @return [Result] of [Unit]
     */
    suspend fun restoreStartStopCharging(): Result<Unit>
}