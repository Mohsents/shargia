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

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

/**
 * Base contract for saving and getting values from DataStore.
 */
interface DataStore {
    suspend fun <K : Preferences.Key<V>, V> save(key: K, value: V): Result<Unit>
    fun <K : Preferences.Key<V>, V> get(key: K, defaultValue: V): Result<Flow<V>>
}