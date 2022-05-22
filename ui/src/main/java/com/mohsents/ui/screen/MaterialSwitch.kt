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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.material.switchmaterial.SwitchMaterial

/**
 * A stateless customized [SwitchMaterial].
 *
 * @param modifier The modifier to be applied to the layout.
 * @param enabled Whether switch enable or not.
 * @param checked Whether switch checked or not.
 * @param configure Configure the switch.
 * @param onCheckedChange Will be called when [checked]'s state change.
*/
@Composable
fun SwitchMaterial(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checked: Boolean,
    configure: (SwitchMaterial) -> Unit = {},
    onCheckedChange: (Boolean) -> Unit,
) {
    val switchMateriel = SwitchMaterial(LocalContext.current)
    AndroidView(modifier = modifier, factory = { switchMateriel }) {
        it.apply {
            configure(this)
            isChecked = checked
            isEnabled = enabled
            setOnCheckedChangeListener { _, isChecked ->
                onCheckedChange(isChecked)
            }
        }
    }
}