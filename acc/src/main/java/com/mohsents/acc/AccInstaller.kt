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

import android.content.Context
import com.mohsents.shared.di.coroutine.IoDispatcher
import com.mohsents.shell.command.Commander
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccInstaller @Inject constructor(
    @ApplicationContext
    private val context: Context,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun installAcc(): Boolean =
        withContext(dispatcher) {
            copyRawFileTo(
                dir = context.cacheDir,
                fileId = R.raw.acc,
                withName = BuildConfig.ACC_FILE,
                context = context
            )

            val installScript = copyRawFileTo(
                dir = context.cacheDir,
                fileId = R.raw.install,
                withName = BuildConfig.ACC_INSTALL_SCRIPT,
                context = context
            )

            val installCommand = "sh ${installScript.absolutePath}"
            Commander.execSu(installCommand)
            return@withContext isAccInstalled()
        }

    /**
     * Uninstall Acc.
     * @return true if successfully uninstalled, false otherwise
     */
    suspend fun uninstallAcc(): Boolean = withContext(dispatcher) {
        Commander.execSu("sh $ACCA --uninstall").isSuccess
    }

    /**
     * Check whether Acc installed or not.
     * @return true if Acc installed, false otherwise
     */
    suspend fun isAccInstalled(): Boolean = withContext(dispatcher) {
        Commander.exec("test -f $ACCA").isSuccess
    }
}
