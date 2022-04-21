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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import com.mohsents.ui.R

const val LOADING_SCREEN_TEST_TAG = "Loading Screen"

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier
        .fillMaxSize()
        .semantics { testTag = LOADING_SCREEN_TEST_TAG }) {
        CircularProgressIndicator(modifier.align(Alignment.Center))
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        Image(
            modifier = modifier
                .width(50.dp)
                .height(50.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = null
        )
        Spacer(modifier = modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.error_screen_text_label),
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
    }
}