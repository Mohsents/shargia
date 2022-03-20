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

package com.mohsents.shared.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.mohsents.shared.util.getResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import com.mohsents.shared.pref.DataStore as DataStoreContract

@Singleton
class DataStorePreference @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val dispatcher: CoroutineDispatcher
) : DataStoreContract {

    override suspend fun <K : Preferences.Key<V>, V> save(key: K, value: V): Result<Unit> =
        withContext(dispatcher) {
            getResult {
                dataStore.edit { preference ->
                    preference[key] = value
                }
                Unit
            }
        }

    override fun <K : Preferences.Key<V>, V> get(key: K, defaultValue: V): Result<Flow<V>> =
        getResult {
            dataStore.data.map { preference ->
                return@map preference[key] ?: defaultValue
            }
        }

    companion object {
        const val PREF_NAME = "shargia"
    }
}