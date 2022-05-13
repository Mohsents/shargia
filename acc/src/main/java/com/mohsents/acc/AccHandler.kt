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
import com.mohsents.acc.util.*
import com.mohsents.shared.di.coroutine.IoDispatcher
import com.mohsents.shared.util.getResult
import com.mohsents.shell.command.Commander
import com.mohsents.shell.command.toSortedString
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class AccHandler @Inject constructor(
    private val accInstaller: AccInstaller,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
) : Acc {

    /**
     * Returns the installed Acc's version code.
     *
     * @return Acc's version code as [Int].
     */
    private suspend fun getInstalledAccVerCode(): Int {
        return exec(ACC_VERSION)
            .substringAfter('(').substringBefore(')').toInt()
    }

    /**
     * Make sure that Acc already installed and has the correct version.
     *
     * If installed Acc is older than bundled, remove the older one and install bundle version.
     *
     * @see getInstalledAccVerCode
     */
    private suspend fun checkAccExistence(): Boolean {
        when {
            accInstaller.isAccInstalled() -> {
                accInstaller.run {
                    val installedVerCode = getInstalledAccVerCode()
                    val bundledVerCode = BuildConfig.ACC_VERSION_CODE.toInt()
                    if (installedVerCode < bundledVerCode) {
                        uninstallAcc()
                        return installAcc()
                    }
                }
            }
            else -> {
                Timber.d("Installing Acc")
                return accInstaller.installAcc()
            }
        }
        // Everything fine so returns true
        return true
    }

    /**
     * Map [resultCode] to its corresponding [AccException].
     *
     * @return [AccException] based on [resultCode]
     */
    private fun mapCodeToException(resultCode: Int): Exception {
        return when (resultCode) {
            1 -> AccException.FailedException()
            2 -> AccException.SyntaxException()
            3 -> AccException.NoBusyboxException()
            4 -> AccException.NoRootException()
            7 -> AccException.FailedToDisableChargingException()
            8 -> AccException.DaemonExistsException()
            9 -> AccException.DaemonNotExistsException()
            10 -> AccException.TestFailedException()
            11 -> AccException.CurrentOutOfRangeException()
            12 -> AccException.InitFailedException()
            13 -> AccException.LockFailedException()
            14 -> AccException.ModuleDisabledException()
            else -> AccException.UnknownException("Acc failed with exit code: $resultCode")
        }
    }

    @Throws(AccException::class)
    override suspend fun exec(
        vararg options: String,
    ): String = withContext(dispatcher) {
        val command = buildString {
            append(ACCA)
            options.forEach { append(" --$it ") }
        }
        val result = Commander.execSu(command)
        if (result.isSuccess) {
            Timber.i("Executed command: [%s]", command.removeSuffix(" "))
            return@withContext result.out.toSortedString()
        } else {
            Timber.d(
                "Failed to execute [${command.removeSuffix(" ")}] cause [${mapCodeToException(result.code)}]"
            )
            throw mapCodeToException(result.code)
        }
    }

    override suspend fun init(): Result<Unit> = getResult {
        val result: Boolean = checkAccExistence()
        Timber.d("Init acc result: %s", result)
        return if (result) Result.success(Unit)
        else Result.failure(Exception("Failed to initialize ACC."))
    }

    override suspend fun getDaemonState(): Result<DaemonState> {
        return try {
            exec(DAEMON_STATUS)
            Result.success(DaemonState.STARTED)
        } catch (e: AccException.DaemonNotExistsException) {
            Result.success(DaemonState.STOPPED)
        } catch (e: AccException.DaemonExistsException) {
            Result.success(DaemonState.STOPPED)
        }
    }

    override suspend fun startDaemon(): Result<Unit> = getResult {
        exec(START_ACC)
    }

    override suspend fun stopDaemon(): Result<Unit> = getResult {
        exec(STOP_ACC)
    }

    override suspend fun getBatteryInfo(): Result<BatteryInfo> = getResult {
        return@getResult exec(BATTERY_INFO).run {
            BatteryInfo(
                capacity = findInfoWithPattern(CAPACITY_PATTERN),
                status = findInfoWithPattern(STATUS_PATTERN, defaultValue = UNKNOWN),
                temp = findInfoWithPattern(TEMP_PATTERN),
                currentNow = findInfoWithPattern(CURRENT_NOW_PATTERN),
                voltageNow = findInfoWithPattern(VOLTAGE_NOW_PATTERN),
                powerNow = findInfoWithPattern(POWER_NOW_PATTERN),
                health = findInfoWithPattern(HEALTH_PATTERN)
            )
        }
    }

    override suspend fun setStartCharging(startAt: Int): Result<Unit> = getResult {
        exec("$SET $RESUME_CAPACITY=$startAt")
    }

    override suspend fun setStopCharging(stopAt: Int): Result<Unit> = getResult {
        exec("$SET $PAUSE_CAPACITY=$stopAt")
    }

    override suspend fun setChargingVoltage(milliVolts: Int): Result<Unit> {
        if (milliVolts !in voltRange) {
            return Result.failure(AccException.UnknownException("milliVolts must be in range $voltRange"))
        }

        return getResult {
            exec(SET, "$CURRENT_VOLTAGE $milliVolts")
        }
    }

    override suspend fun setChargingCurrent(milliAmps: Int): Result<Unit> {
        if (milliAmps !in currentRange) {
            return Result.failure(AccException.UnknownException("milliAmps must be in range $currentRange"))
        }

        return getResult {
            exec(SET, "$CURRENT_AMPERE $milliAmps")
        }
    }

    override suspend fun limitChargingPower(): Result<Unit> = getResult {
        setChargingVoltage(DEFAULT_VOLT)
        setChargingCurrent(DEFAULT_CURRENT)
    }

    override suspend fun restoreChargingPower(): Result<Unit> = getResult {
        exec(SET, "$CURRENT_VOLTAGE -")
        exec(SET, "$CURRENT_AMPERE -")
    }

    override suspend fun restoreStartStopCharging(): Result<Unit> = getResult {
        setStartCharging(0)
        setStopCharging(100)
    }
}