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

import com.mohsents.acc.util.ACC_PATH
import com.mohsents.shell.command.Commander
import com.mohsents.shell.testcommon.RootCheckRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AccInstallerTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val rootRule = RootCheckRule()

    @Inject
    lateinit var accInstaller: AccInstaller

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun ifAccInstalled_uninstallIt() = runTest {
        accInstaller.run {
            if (isAccInstalled()) {
                uninstallAccCompletely()
                assertThat(isAccInstalled(), equalTo(false))
            }
        }
    }

    @Test
    fun ifAccNotInstalled_installIt() = runTest {
        accInstaller.run {
            if (!isAccInstalled()) {
                installAccCompletely()
                assertThat(isAccInstalled(), equalTo(true))
            }
        }
    }

    /**
     * The Acc not installed when it's data files are exist.
     * However we first delete all data files that already exist and then install it again.
     * This is not necessary, but sometimes tests may fail.
     */
    private suspend fun installAccCompletely() {
        val accDataPath = "/data/adb/vr25"
        Commander.execSu("rm -rf $accDataPath/acc")
        Commander.execSu("rm -rf $accDataPath/acc-data")
        accInstaller.installAcc()
    }

    /**
     * Since after uninstalling the Acc, it's binary files not deleted, we delete those as well.
     */
    private suspend fun uninstallAccCompletely() {
        accInstaller.uninstallAcc()
        Commander.execSu("rm -rf $ACC_PATH")
    }
}
