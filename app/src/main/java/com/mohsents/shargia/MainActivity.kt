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

package com.mohsents.shargia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import com.mohsents.ui.screen.InitializationFailedDialog
import com.mohsents.ui.screen.MainScreen
import com.mohsents.ui.screen.RootNotFoundDialog
import com.mohsents.ui.screen.ScreenState
import com.mohsents.ui.theme.ShargiaTheme
import com.mohsents.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val isDeviceRooted = mainViewModel.isDeviceRooted()
            val initAccResult = mainViewModel.initAcc()

            val screenState =
                if (!isDeviceRooted) ScreenState.NO_ROOT
                else if (initAccResult.isFailure) {
                    ScreenState.INITIALIZATION_FAILED
                } else {
                    ScreenState.MAIN_SCREEN
                }

            setContent {
                ShargiaApp(screenState)
            }
        }

        onBackPressedDispatcher.addCallback {
            // Explicitly finish the Activity, So after re-launch screen state will be updated.
            finish()
        }
    }

    @Composable
    fun ShargiaApp(screenState: ScreenState) {
        ShargiaTheme {
            when (screenState) {
                ScreenState.NO_ROOT -> RootNotFoundDialog(onExit = { finish() })
                ScreenState.INITIALIZATION_FAILED -> InitializationFailedDialog(onExit = { finish() })
                ScreenState.MAIN_SCREEN -> MainScreen()
            }
        }
    }
}