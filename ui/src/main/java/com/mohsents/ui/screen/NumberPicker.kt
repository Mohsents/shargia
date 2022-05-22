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

import android.widget.NumberPicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

/**
 * Composes a [NumberPicker].
 *
 * The Number picker allows to the user to pick a number in specific range.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param minValue The minimum value that user can select.
 * @param maxValue The maximum value that user can select.
 * @param defaultValue The value that already selected.
 * @param configure Configure the NumberPicker.
 * @param onValueChange Called every time the value changed and represents selected value.
 */
@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    minValue: Int,
    maxValue: Int,
    defaultValue: Int,
    configure: (NumberPicker) -> Unit = {},
    onValueChange: (Int) -> Unit,
) {
    val context = LocalContext.current

    AndroidView(
        modifier = modifier,
        factory = { NumberPicker(context) }
    ) { numberPicker ->
        numberPicker.apply {
            configure(this)
            this.minValue = minValue
            this.maxValue = maxValue
            value = defaultValue

            setOnValueChangedListener { _, _, newValue ->
                onValueChange(newValue)
            }
        }
    }
}