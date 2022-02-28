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

package com.mohsents.shell.command

import com.mohsents.shell.testcommon.RootCheckRule
import com.topjohnwu.superuser.Shell
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Rule
import org.junit.Test

/**
 * Note: Make sure the test app has root access otherwise [NoRootException] will be thrown.
 * The [RootCheckRule] rule precisely to this.
 * @see RootCheckRule
 */
class CommanderTest {

    @get:Rule
    val rootRule = RootCheckRule()

    @Test
    fun whenExecValidCommand_resultMustBeSuccess() = runTest {
        val result = Commander.exec("ls")
        assertSuccess(result)
    }

    @Test
    fun whenExecInvalidCommand_resultMustBeFailure() = runTest {
        val result = Commander.exec("invalid command!")
        assertFailure(result)
    }

    @Test
    fun whenExecValidSuCommand_resultMustBeSuccess() = runTest {
        val result = Commander.execSu("ls")
        assertSuccess(result)
    }

    @Test
    fun whenExecInvalidSuCommand_resultMustBeFailure() = runTest {
        val result = Commander.execSu("invalid command!")
        assertFailure(result)
    }

    private fun assertSuccess(result: Shell.Result) {
        assertThat(result.isSuccess, equalTo(true))
        assertThat(result.code, equalTo(0))
        assertThat(result.out, `is`(not(emptyList())))
        assertThat(result.err, `is`(emptyList()))
    }

    private fun assertFailure(result: Shell.Result) {
        assertThat(result.isSuccess, equalTo(false))
        assertThat(result.code, `is`(not(0)))
        assertThat(result.out, `is`(emptyList()))
        assertThat(result.err, `is`(emptyList()))
    }
}