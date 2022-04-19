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

package com.mohsents.ui.viewmodel

import com.mohsents.acc.model.BatteryInfo
import com.mohsents.ui.fakeBatteryInfo
import com.mohsents.ui.screen.ScreenState

/**
 * Fake implementation of [MainViewModel] that used in compose previews and ui tests.
 */
object FakeViewViewModel : MainViewModel {

    override suspend fun getScreenState(): ScreenState = ScreenState.MAIN_SCREEN

    override suspend fun getBatteryInfo(): Result<BatteryInfo> = Result.success(fakeBatteryInfo)
}