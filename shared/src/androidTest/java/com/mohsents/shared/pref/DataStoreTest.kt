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

import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class DataStoreTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var dataStore: DataStore

    @Before
    fun initialize() {
        hiltAndroidRule.inject()
    }

    @Test
    fun saveValue_getValue() = runTest {
        val key = stringPreferencesKey("a key")
        val value = "a value"
        val defaultValue = "def value"

        dataStore.save(key, value)

        val result = dataStore.get(key, defaultValue)
        assert(result.isSuccess)
        assertThat(result.getOrNull()?.first(), `is`(not(equalTo(defaultValue))))
        assertThat(result.getOrNull()?.first(), `is`(equalTo(value)))
    }

    @Test
    fun getValue_ifValueNotExist_mustReturnsDefaultValue() = runTest {
        val key = stringPreferencesKey("another key")
        val defaultValue = "def value"

        val result = dataStore.get(key, defaultValue)
        assert(result.isSuccess)
        assertThat(result.getOrNull()?.first(), `is`(equalTo(defaultValue)))
    }
}