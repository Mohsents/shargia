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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohsents.ui.R
import com.mohsents.ui.theme.ShargiaTheme
import com.mohsents.ui.utils.VerticalSpace
import com.mohsents.ui.utils.fakeBatteryInfo
import com.mohsents.ui.viewmodel.FakeViewViewModel
import com.mohsents.ui.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {
    Surface {
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(top = 100.dp, start = 20.dp, end = 20.dp)) {
            Text(text = stringResource(id = R.string.app_name), fontSize = 40.sp)
            VerticalSpace(value = 10.dp)
            BatteryInfoCard(viewModel = viewModel)
            VerticalSpace(value = 10.dp)
            PreferenceScreen(viewModel = viewModel)
            VerticalSpace(value = 30.dp)
            Column {
                CompositionLocalProvider(
                    values = arrayOf(
                        LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f))
                ) {
                    Icon(imageVector = Icons.Outlined.Info, contentDescription = null)
                    VerticalSpace(value = 20.dp)
                    Text(
                        text = stringResource(id = R.string.setting_info_description_text),
                        fontSize = 14.sp
                    )
                }
            }
            VerticalSpace(value = 100.dp)
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