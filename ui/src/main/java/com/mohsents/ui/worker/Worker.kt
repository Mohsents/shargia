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

package com.mohsents.ui.worker

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mohsents.acc.Acc
import com.mohsents.shared.util.NotificationUtils
import com.mohsents.ui.R
import com.mohsents.ui.pref.UserPreference
import com.mohsents.ui.pref.UserPreferenceImpl
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import timber.log.Timber

@HiltWorker
class Worker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val userPreference: UserPreference,
    private val acc: Acc,
    private val notificationUtils: NotificationUtils,
) : CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {
        Timber.d("ServiceWorker started.")
        val serviceEnabled = userPreference.get(UserPreferenceImpl.SERVICE_KEY, false)

        if (serviceEnabled) {
            delay(1000) // Make some delay to start daemon.
            acc.startDaemon().onFailure {
                userPreference.save(UserPreferenceImpl.SERVICE_KEY, false)
                notifyServiceDisabled()
            }
        } else {
            acc.stopDaemon()
        }
        return Result.success()
    }

    private fun notifyServiceDisabled() {
        val notification =
            notificationUtils.buildNotification(appContext.getString(R.string.service_notification_channel_id)) {
                setContentTitle(appContext.getString(R.string.service_notification_content_title))
                setContentText(appContext.getString(R.string.service_notification_content_text))
                setSmallIcon(R.drawable.ic_flash)
                priority = NotificationCompat.PRIORITY_HIGH
                setAutoCancel(true)
                setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                setCategory(NotificationCompat.CATEGORY_STATUS)
            }
        val notificationId = appContext.getString(R.string.service_notification_id).toInt()
        notificationUtils.getNotificationManager().notify(notificationId, notification)
    }
}