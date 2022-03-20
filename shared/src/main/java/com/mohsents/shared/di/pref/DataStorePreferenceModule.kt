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

package com.mohsents.shared.di.pref

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.mohsents.shared.di.coroutine.IoDispatcher
import com.mohsents.shared.pref.DataStore
import com.mohsents.shared.pref.DataStorePreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataStorePreferenceModule {

    private val Context.dataStore by preferencesDataStore(name = DataStorePreference.PREF_NAME)

    @Singleton
    @Provides
    fun providePreferenceStorage(
        @ApplicationContext context: Context,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): DataStore =
        DataStorePreference(context.dataStore, dispatcher)
}