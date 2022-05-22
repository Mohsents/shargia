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

@file:OptIn(ExperimentalMaterial3Api::class)

package com.mohsents.ui.screen

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mohsents.acc.model.BatteryInfo
import com.mohsents.ui.R
import com.mohsents.ui.theme.ShargiaTheme
import com.mohsents.ui.utils.LaunchWhenResumed
import com.mohsents.ui.utils.fakeBatteryInfo
import com.mohsents.ui.utils.isInLandscape
import com.mohsents.ui.utils.localize
import com.mohsents.ui.viewmodel.MainViewModel

@Composable
fun BatteryInfoCard(viewModel: MainViewModel) {
    LaunchWhenResumed {
        viewModel.updateUiState()
    }

    val (isLoading, isError, batteryInfo) = viewModel.uiState.value
    when {
        isLoading -> LoadingScreen()
        isError -> ErrorScreen()
        else -> BatteryInfo(batteryInfo = batteryInfo)
    }
}

@Composable
private fun BatteryInfo(batteryInfo: BatteryInfo) {
    val context = LocalContext.current
    val cardPadding = if (isInLandscape(context)) 100.dp else 0.dp

    Card(
        modifier = Modifier.padding(start = cardPadding, end = cardPadding),
        colors = cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        val (
            capacity,
            status,
            temp,
            currentNow,
            voltageNow,
            powerNow,
            health,
        ) = batteryInfo.localize(context)

        Row {
            Column(Modifier.weight(1f)) {
                BatteryInfoText(name = R.string.battery_info_status_label, value = status)
                BatteryInfoText(name = R.string.battery_info_voltage_label, value = voltageNow)
                BatteryInfoText(name = R.string.battery_info_power_label, value = powerNow)
            }

            Column(Modifier.weight(1f)) {
                BatteryInfoText(name = R.string.battery_info_temp_label, value = temp)
                BatteryInfoText(name = R.string.battery_info_current_label, value = currentNow)
                BatteryInfoText(name = R.string.battery_info_health_label, value = health)
            }
        }

        ConstraintLayout(
            Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp, bottom = 10.dp)
        ) {
            val (flashIcon, capacityText) = createRefs()
            val bottomPadding = 6.dp
            var isCharging by remember { mutableStateOf(false) }
            isCharging = status == context.getString(R.string.battery_info_charging_status_label)

            if (isCharging) {
                val infiniteTransition = rememberInfiniteTransition()
                val alpha by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(700),
                        repeatMode = RepeatMode.Reverse
                    )
                )

                Image(
                    modifier = Modifier
                        .width(24.dp)
                        .height(28.dp)
                        .constrainAs(flashIcon) {
                            bottom.linkTo(parent.bottom, margin = bottomPadding)
                            absoluteRight.linkTo(capacityText.absoluteLeft)
                        },
                    painter = painterResource(id = R.drawable.ic_flash),
                    alpha = alpha,
                    contentScale = ContentScale.Inside,
                    contentDescription = null
                )
            }

            Text(
                text = capacity,
                fontSize = 32.sp,
                modifier = Modifier.constrainAs(capacityText) {
                    absoluteLeft.linkTo(flashIcon.absoluteRight)
                })
        }
    }
}

@Composable
private fun BatteryInfoText(
    modifier: Modifier = Modifier,
    @StringRes name: Int,
    value: String,
) {
    Text(
        text = "${stringResource(id = name)}: $value",
        modifier = modifier.padding(start = 20.dp, top = 15.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun BatteryInfoLight() {
    ShargiaTheme {
        BatteryInfo(fakeBatteryInfo)
    }
}

@Preview(
    device = Devices.AUTOMOTIVE_1024p,
    widthDp = 720, heightDp = 360
)
@Composable
private fun BatteryInfoLandscape() {
    ShargiaTheme {
        BatteryInfo(fakeBatteryInfo)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BatteryInfoDark() {
    ShargiaTheme {
        BatteryInfo(fakeBatteryInfo)
    }
}