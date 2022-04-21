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

package com.mohsents.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.mohsents.ui.screen.BatteryInfoCard
import com.mohsents.ui.screen.LOADING_SCREEN_TEST_TAG
import com.mohsents.ui.screen.UiState
import com.mohsents.ui.viewmodel.FakeViewViewModel
import org.junit.Rule
import org.junit.Test

class BatteryInfoCardTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun batteryInfoCard_loadingStateTest() {
        FakeViewViewModel.updateUiState(UiState(isLodging = true, isError = false))
        composeRule.setContent {
            BatteryInfoCard(viewModel = FakeViewViewModel)
        }
        composeRule.onNodeWithTag(LOADING_SCREEN_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun batteryInfoCard_ErrorStateTest() {
        FakeViewViewModel.updateUiState(UiState(isLodging = false, isError = true))
        composeRule.setContent {
            BatteryInfoCard(viewModel = FakeViewViewModel)
        }
        composeRule
            .onNodeWithText(getStringById(R.string.error_screen_text_label))
            .assertIsDisplayed()
    }

    @Test
    fun batteryInfoCard_verify_properties() {
        FakeViewViewModel.updateUiState(UiState(isLodging = false))
        composeRule.setContent {
            BatteryInfoCard(viewModel = FakeViewViewModel)
        }
        val batteryInfo = FakeViewViewModel.uiState.value.batteryInfo
        composeRule.run {
            onNodeWithText("${getStringById(R.string.battery_info_status_label)}: ${batteryInfo.status}")
            onNodeWithText("${getStringById(R.string.battery_info_temp_label)}: ${batteryInfo.temp}")
            onNodeWithText("${getStringById(R.string.battery_info_current_label)}: ${batteryInfo.currentNow}")
            onNodeWithText("${getStringById(R.string.battery_info_voltage_label)}: ${batteryInfo.voltageNow}")
            onNodeWithText("${getStringById(R.string.battery_info_power_label)}: ${batteryInfo.powerNow}")
            onNodeWithText("${getStringById(R.string.battery_info_health_label)}: ${batteryInfo.health}")
            onNodeWithText(batteryInfo.capacity)
        }.assertIsDisplayed()
    }
}