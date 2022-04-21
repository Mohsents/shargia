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

package com.mohsents.acc.di

import com.mohsents.acc.Acc
import com.mohsents.acc.AccHandler
import com.mohsents.acc.AccInstaller
import com.mohsents.shared.di.coroutine.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccModule {

    @Provides
    @Singleton
    fun provideAcc(
        accInstaller: AccInstaller,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): Acc {
        return AccHandler(accInstaller, dispatcher)
    }
}