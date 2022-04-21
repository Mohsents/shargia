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

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsents.acc.Acc
import com.mohsents.shell.root.RootHandler
import com.mohsents.ui.pref.UiPreference
import com.mohsents.ui.pref.UserPreference
import com.mohsents.ui.screen.ScreenState
import com.mohsents.ui.screen.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val acc: Acc
) : ViewModel(), MainViewModel {

    private val _uiState: MutableState<UiState> = mutableStateOf(UiState())
    override val uiState: State<UiState> = _uiState

    override fun updateUiState() {
        viewModelScope.launch {
            while (true) {
                delay(DELAY_UPDATE_BATTERY_INFO)
                acc.getBatteryInfo().onSuccess { batteryInfo ->
                    _uiState.value =
                        UiState(isLodging = false, isError = false, batteryInfo = batteryInfo)
                }.onFailure {
                    _uiState.value = UiState(isLodging = false, isError = true)
                    return@launch
                }
            }
        }
    }

    override suspend fun getScreenState(): ScreenState = when {
        !RootHandler.isShellRooted() -> ScreenState.NO_ROOT
        acc.init().isFailure -> ScreenState.INITIALIZATION_FAILED
        else -> ScreenState.MAIN_SCREEN
    }

    companion object {
        private const val DELAY_UPDATE_BATTERY_INFO = 2000L
    }
}