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
import androidx.compose.ui.test.onNodeWithText
import com.mohsents.ui.screen.ScreenState
import com.mohsents.ui.screen.ShargiaApp
import com.mohsents.ui.viewmodel.FakeViewViewModel
import org.junit.Rule
import org.junit.Test

class ShargiaAppTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shargiaApp_NoRootDialogTest() {
        composeTestRule.setContent {
            ShargiaApp(
                viewModel = FakeViewViewModel,
                screenState = ScreenState.NO_ROOT
            )
        }
        composeTestRule
            .onNodeWithContentDescription(R.string.root_not_found_dialog_content_description)
            .assertIsDisplayed()
    }

    @Test
    fun shariahApp_InitialisationFailedDialogTest() {
        composeTestRule.setContent {
            ShargiaApp(
                viewModel = FakeViewViewModel,
                screenState = ScreenState.INITIALIZATION_FAILED
            )
        }
        composeTestRule
            .onNodeWithContentDescription(R.string.acc_init_failed_dialog_content_description)
            .assertIsDisplayed()
    }

    @Test
    fun shargiaApp_MainScreenTest() {
        composeTestRule.setContent {
            ShargiaApp(
                viewModel = FakeViewViewModel,
                screenState = ScreenState.MAIN_SCREEN
            )
        }
        composeTestRule
            .onNodeWithText(getStringById(R.string.app_name))
            .assertIsDisplayed()
    }
}