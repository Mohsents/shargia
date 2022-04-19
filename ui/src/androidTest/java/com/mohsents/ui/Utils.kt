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

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.hasContentDescription
import androidx.test.platform.app.InstrumentationRegistry

fun SemanticsNodeInteractionsProvider.onNodeWithContentDescription(
    @StringRes label: Int,
    substring: Boolean = false,
    ignoreCase: Boolean = false,
    useUnmergedTree: Boolean = false
): SemanticsNodeInteraction {
    val contentDiscretion = getStringById(label)
    return onNode(hasContentDescription(contentDiscretion, substring, ignoreCase), useUnmergedTree)
}

fun getStringById(@StringRes id: Int): String {
    val appContext: Context = InstrumentationRegistry.getInstrumentation().context
    return appContext.getString(id)
}