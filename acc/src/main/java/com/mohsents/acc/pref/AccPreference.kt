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

import androidx.datastore.preferences.core.stringPreferencesKey
import com.mohsents.acc.DaemonState
import com.mohsents.shared.pref.DataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccPreference @Inject constructor(
    private val dataStore: DataStore
) {

    suspend fun saveDaemonState(state: DaemonState): Result<Unit> =
        dataStore.save(DAEMON_STATE, state.name)

    suspend fun getDaemonState(): DaemonState {
        val result = dataStore.get(DAEMON_STATE, DaemonState.STOPPED.name).getOrNull()
        return when (result?.first()) {
            DaemonState.STARTED.name -> DaemonState.STARTED
            else -> DaemonState.STOPPED
        }
    }

    private companion object {
        val DAEMON_STATE = stringPreferencesKey("daemon-state")
    }
}