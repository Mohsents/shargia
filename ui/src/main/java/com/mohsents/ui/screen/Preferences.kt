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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.text.isDigitsOnly
import com.mohsents.ui.R
import com.mohsents.ui.utils.VerticalSpace
import com.mohsents.ui.utils.rememberMutableState

/**
 * Opens a dialog to user chose a number.
 *
 * @param title Title of this preference.
 * @param summary Description of this preference.
 * @param enabled whether enable or not.
 * @param minValue Minimum value for the number picker.
 * @param maxValue Maximum value for the number picker.
 * @param defaultValue Initial value for the number picker.
 * @param onSelectValue Called when user clicks on confirm button and picked a value.
 */
@Composable
fun NumberPickerPreference(
    title: String,
    summary: String,
    enabled: Boolean = true,
    minValue: Int,
    maxValue: Int,
    defaultValue: Int = 0,
    onSelectValue: (Int) -> Unit,
) {
    var openDialog by rememberMutableState(value = false)
    val minValueState by rememberMutableState(value = minValue)
    val maxValueState by rememberMutableState(value = maxValue)
    var value by rememberMutableState(value = defaultValue)

    Preference(
        title = title,
        summary = summary,
        enabled = enabled
    ) {
        if (enabled) {
            openDialog = true
        }
    }

    if (openDialog) {
        DialogPreferencePlaceHolder(
            title = title,
            onDismiss = { openDialog = false },
            onConfirm = { onSelectValue(value); openDialog = false },
        ) {
            NumberPicker(
                modifier = Modifier.fillMaxWidth(),
                minValue = minValueState,
                maxValue = maxValueState,
                defaultValue = defaultValue,
                onValueChange = { value = it }
            )
        }
    }
}

/**
 * Displays a dialog that user can enter new value for the summary.
 *
 * @param title Title of this preference.
 * @param summary Description of this preference.
 * @param placeHolderText
 * @param enabled whether enable or not. If disabled, it's summary also will be disable.
 * @param maxLength Maximum length of character that user can enter.
 * @param keyboardOptions Configure the Keyboard via [KeyboardOptions].
 * @param isError Called when user clicks on [onEditSummery], it be called and checks whether the input is valid or not.
 * If returns true, the error will be shown to the user.
 * @param errorText The error message will be shown to the user if [isError] returns true.
 * @param onEditSummery Will be called when the user edited the summary.
 */
@Composable
fun EditTextPreference(
    title: String,
    summary: String,
    placeHolderText: String = summary,
    enabled: Boolean = true,
    maxLength: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isError: (String) -> Boolean = { false },
    errorText: String = "",
    onEditSummery: (summery: String) -> Unit,
) {
    var openDialog by rememberMutableState(value = false)

    Preference(
        title = title,
        summary = summary,
        enabled = enabled
    ) {
        if (enabled) {
            openDialog = true
        }
    }

    fun closeDialog() {
        openDialog = false
    }

    if (openDialog) {
        var text by rememberMutableState(value = "")
        var errorState by rememberMutableState(value = false)

        fun isInputValid(text: String): Boolean =
            text.isNotEmpty() && text.length <= maxLength

        DialogPreferencePlaceHolder(
            title = title,
            onDismiss = { closeDialog() },
            confirmEnabled = text.isNotEmpty(),
            onConfirm = {
                if (isError(text)) {
                    errorState = true
                } else {
                    onEditSummery(text); closeDialog()
                }
            }
        ) {
            OutlinedTextField(
                singleLine = true,
                isError = errorState,
                value = text,
                keyboardOptions = keyboardOptions,
                onValueChange = {
                    // Show place holder text.
                    if (it.isEmpty()) text = ""

                    // Prevent to enter non-digit characters.
                    if (keyboardOptions.keyboardType == KeyboardType.Number) {
                        if (isInputValid(it) && it.isDigitsOnly()) text = it
                    } else if (isInputValid(it)) {
                        text = it
                    }

                    errorState = false
                },
                placeholder = { Text(text = placeHolderText) },
                trailingIcon = {
                    if (errorState) {
                        Icon(painter = painterResource(id = R.drawable.ic_error),
                            contentDescription = errorText,
                            tint = MaterialTheme.colorScheme.error)
                    }
                })

            if (errorState) {
                Text(
                    text = errorText,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
        }
    }
}

/**
 * A [SwitchPreference] with [Card] around it.
 *
 * Typically used for controlling several preferences.
 *
 * @param title Title of this preference.
 * @param summary Description of this preference.
 * @param enabled whether enable or not. If disabled, it's switch also will be disable.
 * @param checked whether switch checked or not.
 * @param onCheckedChange Called when the user clicks on Preference.
 */
@Composable
fun CategorySwitchPreference(
    title: String,
    summary: String = "",
    enabled: Boolean = true,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(40),
        colors = cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        val innerPadding = 20.dp
        SwitchPreference(
            modifier = Modifier.padding(
                start = innerPadding,
                end = innerPadding
            ),
            title = title,
            summary = summary,
            enabled = enabled,
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

/**
 * A switch preference provides a way to user to toggle on or off an particular preference.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param title Title of this preference.
 * @param summary Description of this preference.
 * @param enabled whether enable or not. If disabled, it's switch also will be disable.
 * @param checked whether switch checked or not.
 * @param onCheckedChange Called when the user clicks on Preference.
 * @param content The content to be displayed under the switchPreference.
 */
@Composable
fun SwitchPreference(
    modifier: Modifier = Modifier,
    title: String,
    summary: String = "",
    enabled: Boolean = true,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    var checkedState by rememberMutableState(value = checked)
    val enabledState by rememberMutableState(value = enabled)

    Column {
        ConstraintLayout(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    if (enabledState) {
                        checkedState = !checkedState
                        onCheckedChange(checkedState)
                    }
                }
        ) {
            val (preferenceRef, switchRef) = createRefs()

            Preference(
                modifier = Modifier
                    .constrainAs(preferenceRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(end = 100.dp),
                title = title,
                summary = summary,
                enabled = enabledState
            )

            SwitchMaterial(
                modifier = Modifier.constrainAs(switchRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
                enabled = enabledState,
                checked = checkedState,
                configure = { it.isClickable = false },
                onCheckedChange = onCheckedChange
            )
        }
        content()
    }
}

/**
 * Represents preference that have a title and summary.
 *
 * @param title Title of preference.
 * @param summary Summary of preference.
 * @param enabled Whether the preference is enabled or not. If disabled, [onClick] not be called.
 * @param onClick Called when user clicks on the preference.
 */
@Composable
fun Preference(
    modifier: Modifier = Modifier,
    title: String,
    summary: String = "",
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
) {
    val innerPadding = if (summary.isNotEmpty()) 10.dp else 20.dp
    val columnModifier = modifier
        .fillMaxWidth()
        .padding(vertical = innerPadding)

    Column(modifier = if (onClick != null) modifier
        .clickable {
            if (enabled) onClick()
        }
        .then(columnModifier)
    else columnModifier
    ) {
        Text(text = title, fontSize = 20.sp, maxLines = 2)

        if (summary.isNotEmpty()) {
            VerticalSpace(value = 2.dp)
            Text(
                text = summary,
                fontSize = 14.sp,
                color = LocalContentColor.current.copy(alpha = if (enabled) 0.75f else 0.30f)
            )
        }
    }
}