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

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohsents.ui.R
import com.mohsents.ui.fakeBatteryInfo
import com.mohsents.ui.theme.ShargiaTheme
import com.mohsents.ui.viewmodel.FakeViewViewModel
import com.mohsents.ui.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {
    Surface {
        Column(modifier = Modifier.padding(top = 100.dp, start = 20.dp, end = 20.dp)) {
            Text(text = stringResource(id = R.string.app_name), fontSize = 40.sp)
            Spacer(modifier = Modifier.height(10.dp))
            BatteryInfoCard(viewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreviewLight() {
    ShargiaTheme {
        FakeViewViewModel.updateUiState(UiState(isLodging = false, batteryInfo = fakeBatteryInfo))
        MainScreen(FakeViewViewModel)
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreviewDark() {
    ShargiaTheme {
        MainScreen(FakeViewViewModel)
    }
}