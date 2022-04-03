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

package com.mohsents.ui.screen

/***
 * Represent state of the screen.
 *
 * [ScreenState.NO_ROOT]: Means root not available.
 * [ScreenState.INITIALIZATION_FAILED]: Means that initialization of the Acc was failed.
 * [ScreenState.MAIN_SCREEN]: States the Main screen.
 */
enum class ScreenState {
    NO_ROOT, INITIALIZATION_FAILED, MAIN_SCREEN
}