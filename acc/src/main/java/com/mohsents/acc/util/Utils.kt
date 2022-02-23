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

package com.mohsents.acc.util

import android.content.Context
import androidx.annotation.RawRes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

/**
 * Copy specified raw file with [fileId] to [dir].
 *
 * @param withName name of file that will be copied in [dir]
 * @param context for accessing to raw resources
 * @return copied [File]
 */
@Suppress("BlockingMethodInNonBlockingContext")
suspend fun copyRawFileTo(
    dir: File,
    @RawRes fileId: Int,
    withName: String,
    context: Context
): File = withContext(Dispatchers.IO) {
    val destFile = File(dir, withName)
    context.resources.openRawResource(fileId).use { input ->
        FileOutputStream(destFile).use { output ->
            input.copyTo(output)
        }
    }
    return@withContext destFile
}
