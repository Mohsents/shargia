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

package com.mohsents.shargia

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.lifecycleScope
import com.mohsents.shared.util.NotificationUtils
import com.mohsents.ui.R
import com.mohsents.ui.screen.LoadingScreen
import com.mohsents.ui.screen.ShargiaApp
import com.mohsents.ui.theme.ShargiaTheme
import com.mohsents.ui.viewmodel.ViewModel
import com.mohsents.ui.worker.ServiceWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ViewModel by viewModels()

    @Inject
    lateinit var notificationUtils: NotificationUtils

    @Inject
    lateinit var serviceWorker: ServiceWorker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ShargiaTheme {
                LoadingScreen()
            }
        }

        lifecycleScope.launch {
            val screenState = viewModel.getScreenState()
            setContent {
                ShargiaApp(
                    viewModel = viewModel,
                    screenState = screenState
                ) { finish() }
            }
        }

        onBackPressedDispatcher.addCallback {
            // Explicitly finish the Activity, So after re-launch screen state will be updated.
            finish()
        }

        serviceWorker.start()

        notificationUtils.createChannel(
            channelId = resources.getString(R.string.service_notification_channel_id),
            channelName = resources.getString(R.string.service_notification_channel_name),
            importance = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                NotificationManagerCompat.IMPORTANCE_HIGH else 0
        ) {
            lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
        }
    }
}