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

import androidx.compose.runtime.Composable
import com.mohsents.ui.theme.ShargiaTheme
import com.mohsents.ui.viewmodel.MainViewModel

/**
 * Start point of the app.
 *
 * @param viewModel Main viewModel of the app.
 * @param screenState (State) current state of the screen that must be show.
 * @param onExit (Callback) when the user tap on the exit button of the dialog, this will be called.
 */
@Composable
fun ShargiaApp(
    viewModel: MainViewModel,
    screenState: ScreenState,
    onExit: () -> Unit = {}
) {
    ShargiaTheme {
        when (screenState) {
            ScreenState.NO_ROOT -> RootNotFoundDialog(onExit = { onExit() })
            ScreenState.INITIALIZATION_FAILED -> InitializationFailedDialog(onExit = { onExit() })
            ScreenState.MAIN_SCREEN -> MainScreen(viewModel)
        }
    }
}