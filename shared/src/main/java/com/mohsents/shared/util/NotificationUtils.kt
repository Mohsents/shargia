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

package com.mohsents.shared.util

import android.app.Notification
import android.app.NotificationChannel
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationUtils @Inject constructor(
    @ApplicationContext
    private val appContext: Context,
) {

    fun buildNotification(
        channelId: String,
        config: NotificationCompat.Builder.() -> Unit,
    ): Notification {
        return NotificationCompat.Builder(appContext, channelId).apply { config(this) }.build()
    }

    fun getNotificationManager(config: NotificationManagerCompat.() -> Unit = {}): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(appContext)
        return notificationManager.apply {
            config(this)
        }
    }

    fun createChannel(
        channelId: String,
        channelName: String,
        importance: Int,
        config: NotificationChannel.() -> Unit = {},
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                importance
            )
            notificationChannel.apply {
                config(this)
            }
            getNotificationManager().createNotificationChannel(notificationChannel)
        }
    }
}