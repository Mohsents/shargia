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

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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
    onExit: () -> Unit
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
    onExit: () -> Unit
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