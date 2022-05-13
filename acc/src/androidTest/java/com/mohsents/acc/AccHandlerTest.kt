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

import com.mohsents.acc.util.DEFAULT_BATTERY_INFO_VALUE
import com.mohsents.acc.util.DEFAULT_CURRENT
import com.mohsents.acc.util.DEFAULT_VOLT
import com.mohsents.shell.testcommon.RootCheckRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AccHandlerTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    val rootRule = RootCheckRule()

    @Inject
    lateinit var acc: Acc

    @Before
    fun initialize() {
        hiltAndroidRule.inject()
    }

    @Test
    fun init_mustRunSuccessfully() = runTest {
        assert(acc.init().isSuccess)
    }

    @Test
    fun startDaemon_DaemonStateMustEqualTo_STARTED() = runTest {
        acc.apply {
            // Stop daemon if it already running
            stopDaemon()
            startDaemon()
            getDaemonState().onSuccess {
                assertThat(it, equalTo(DaemonState.STARTED))
            }
        }
    }

    @Test
    fun stopDaemon_DaemonStateMustEqualTo_STOPPED() = runTest {
        acc.apply {
            // Start daemon if it already not running
            startDaemon()
            stopDaemon()
            getDaemonState().onSuccess {
                assertThat(it, equalTo(DaemonState.STOPPED))
            }
        }
    }

    @Test
    fun getBatteryInfo_mustNotHaveInvalidInfo() = runTest {
        fun assertNotEqual(
            property: String,
            defaultValue: String = DEFAULT_BATTERY_INFO_VALUE,
        ) = assertThat(property, not(equalTo(defaultValue)))

        acc.getBatteryInfo().onSuccess {
            assertNotEqual(it.capacity)
            assertNotEqual(it.status)
            assertNotEqual(it.temp)
            assertNotEqual(it.voltageNow)
            assertNotEqual(it.currentNow)
            assertNotEqual(it.powerNow)
            assertNotEqual(it.health)
        }
    }

    @Test
    fun setStartCharging_mustRunSuccessfully() = runTest {
        assert(acc.setStartCharging(25).isSuccess)
    }

    @Test
    fun setStopCharging_mustRunSuccessfully() = runTest {
        assert(acc.setStopCharging(100).isSuccess)
    }

    @Test
    fun limitChargingPower_mustRunSuccessfully() = runTest {
        assert(acc.limitChargingPower().isSuccess)
    }

    @Test
    fun setChargingVoltage_mustRunSuccessfully() = runTest {
        assert(acc.setChargingVoltage(DEFAULT_VOLT).isSuccess)
    }

    @Test
    fun setChargingCurrent_mustRunSuccessfully() = runTest {
        assert(acc.setChargingCurrent(DEFAULT_CURRENT).isSuccess)
    }

    @Test
    fun setChargingVoltage_IfGetsInvalidParams_ResultMustBeFailure() = runTest {
        assert(acc.setChargingVoltage(-1).isFailure)
    }

    @Test
    fun setChargingCurrent_IfGetsInvalidParams_ResultMustBeFailure() = runTest {
        assert(acc.setChargingCurrent(-1).isFailure)
    }

    @Test
    fun setChargingVoltage_restoreChargingPower_mustBeSuccess() = runTest {
        val voltage = 4300
        acc.setChargingVoltage(voltage)

        val result = acc.restoreChargingPower()

        assert(result.isSuccess)
    }

    @Test
    fun setChargingCurrent_restoreChargingPower_mustBeSuccess() = runTest {
        val current = 9999
        acc.setChargingCurrent(current)

        val result = acc.restoreChargingPower()

        assert(result.isSuccess)
    }

    @Test
    fun setStartCharging_restoreStartStopCharging_mustBeSuccess() = runTest {
        val startAt = 25
        acc.setStartCharging(startAt)

        val result = acc.restoreStartStopCharging()

        assert(result.isSuccess)
    }

    @Test
    fun setStopCharging_restoreStartStopCharging_mustBeSuccess() = runTest {
        val stopAt = 85
        acc.setStartCharging(stopAt)

        val result = acc.restoreStartStopCharging()

        assert(result.isSuccess)
    }
}