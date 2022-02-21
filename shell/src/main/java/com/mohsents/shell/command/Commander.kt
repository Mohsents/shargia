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

import com.topjohnwu.superuser.Shell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object Commander {

    /**
     * Execute [commands] in shell without root privilege.
     *
     * @param commands array of commands to execute.
     * @return [Shell.Result]
     */
    suspend fun execCommand(vararg commands: String): Shell.Result = withContext(Dispatchers.IO) {
        Shell.sh(*commands).exec()
    }

    /**
     * Execute [commands] in the shell with root privilege.
     * Note that if the shell do not have root access, the [commands] will not be executed.
     *
     * @param commands array of commands to execute.
     * @return [Shell.Result]
     */
    suspend fun execSuCommand(vararg commands: String): Shell.Result = withContext(Dispatchers.IO) {
        Shell.su(*commands).exec()
    }
}