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

package com.mohsents.acc.pref

import com.mohsents.acc.DaemonState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AccPreferenceTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var accPreference: AccPreference

    @Before
    fun initialize() {
        hiltAndroidRule.inject()
    }

    @Test
    fun saveDaemonStateToStartedState_getDaemonStateMustEqualToStarted() = runTest {
        accPreference.saveDaemonState(DaemonState.STARTED)

        val result = accPreference.getDaemonState()
        assertThat(result, `is`(equalTo(DaemonState.STARTED)))
    }

    @Test
    fun saveDaemonStateToStoppedState_getDaemonStateMustEqualToStopped() = runTest {
        accPreference.saveDaemonState(DaemonState.STOPPED)

        val result = accPreference.getDaemonState()
        assertThat(result, `is`(equalTo(DaemonState.STOPPED)))
    }
}