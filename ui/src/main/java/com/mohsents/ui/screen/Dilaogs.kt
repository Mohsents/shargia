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

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.mohsents.ui.R

@Composable
fun RootNotFoundDialog(
    modifier: Modifier = Modifier,
    onExit: () -> Unit
) {
    val context = LocalContext.current
    ErrorDialog(
        modifier = modifier.semantics {
            contentDescription =
                context.getString(R.string.root_not_found_dialog_content_description)
        },
        title = stringResource(id = R.string.root_not_found_dialog_title),
        text = stringResource(id = R.string.root_not_found_dialog_text),
        onExit = onExit
    )
}

@Composable
fun InitializationFailedDialog(
    modifier: Modifier = Modifier,
    onExit: () -> Unit,
) {
    val context = LocalContext.current
    ErrorDialog(
        modifier = modifier.semantics {
            contentDescription =
                context.getString(R.string.acc_init_failed_dialog_content_description)
        },
        title = stringResource(id = R.string.acc_init_failed_dialog_title),
        text = stringResource(id = R.string.acc_init_failed_dialog_text),
        onExit = onExit
    )
}

/**
 * Displays an [AlertDialog] with error context.
 *
 * @param modifier Modifier
 * @param title title of [AlertDialog]
 * @param text text of [AlertDialog]
 * @param onExit (callback) Called when the user tries to dismiss
 * the Dialog by clicking outside or pressing the confirm button.
 */
@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    onExit: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        title = { Text(text = title) },
        text = { Text(text = text) },
        confirmButton = {
            Button(onClick = onExit) {
                Text(text = stringResource(id = R.string.close_label))
            }
        },
        onDismissRequest = onExit,
    )
}

/**
 * Opens a dialog with the given content.
 *
 * Represents a basic dialog with a title and acton buttons to cancel and confirm.
 *
 * @param title Title that placed as the header of the dialog.
 * @param confirmEnabled Whether the confirm button enabled or not.
 * @param onDismiss Called when the user clicks on cancel button.
 * @param onConfirm Called when the user clicks on confirm button.
 * @param content The content to be displayed inside the dialog.
 */
@Composable
fun DialogPreferencePlaceHolder(
    title: String,
    confirmEnabled: Boolean = true,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            val innerPadding = 24.dp
            Column {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(0.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = innerPadding, vertical = 15.dp),
                        text = title,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                ConstraintLayout(modifier = Modifier.padding(innerPadding)) {
                    val (contentRef, buttonsRef) = createRefs()

                    Column(Modifier.constrainAs(contentRef) {}) {
                        content()
                    }

                    Row(horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(buttonsRef) {
                                top.linkTo(contentRef.bottom, margin = innerPadding)
                            }
                    ) {
                        DialogActionButton(
                            text = stringResource(id = R.string.cancel_label),
                            onClick = onDismiss
                        )

                        DialogActionButton(
                            text = stringResource(id = R.string.ok_label),
                            enabled = confirmEnabled,
                            onClick = onConfirm
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DialogActionButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = Modifier.padding(end = 8.dp),
        onClick = onClick,
        enabled = enabled
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}