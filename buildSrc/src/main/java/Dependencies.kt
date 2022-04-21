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

/**
 * Define libraries dependencies.
 */
object Dependencies {

    object Plugins {
        const val AGP = "com.android.tools.build:gradle:${Versions.AGP}"
        const val KOTLIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
        const val HILT = "com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}"
    }

    object Libs {
        const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
        const val LIBSU = "com.github.topjohnwu.libsu:core:${Versions.LIBSU}"
        const val COROUTINE_CORE =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINE}"
        const val COROUTINES_TEST =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINE}"
        const val DATASTORE = "androidx.datastore:datastore-preferences:${Versions.DATASTORE}"
        const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"
        const val COMPOSE_UI = "androidx.compose.ui:ui:${Versions.COMPOSE}"
        const val ANNOTATION = "androidx.annotation:annotation:${Versions.ANNOTATION}"
        const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE}"
        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
        const val LIFECYCLE = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ARCH}"
        const val COMPOSE_TOOLING_PREVIEW =
            "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE}"
        const val COMPOSE_FOUNDATION = "androidx.compose.foundation:foundation:${Versions.COMPOSE}"
        const val COMPOSE_MATERIAL3 =
            "androidx.compose.material3:material3:${Versions.COMPOSE_MATERIAL3}"
        const val COMPOSE_MATERIAL_ICONS =
            "androidx.compose.material:material-icons-core:${Versions.COMPOSE}"
        const val COMPOSE_CONSTRAINT_LAYOUT =
            "androidx.constraintlayout:constraintlayout-compose:${Versions.COMPOSE_CONSTRAINT_LAYOUT}"
        const val COMPOSE_ACTIVITY =
            "androidx.activity:activity-compose:${Versions.COMPOSE_ACTIVITY}"
        const val COMPOSE_VIEWMODEL =
            "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.ARCH}"

        // Test
        const val HILT = "com.google.dagger:hilt-android:${Versions.HILT}"
        const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
        const val HILT_ANDROID_TESTING = "com.google.dagger:hilt-android-testing:${Versions.HILT}"
        const val JUNIT = "junit:junit:${Versions.JUNIT}"
        const val ANDROIDX_JUNIT = "androidx.test.ext:junit-ktx:${Versions.ANDROIDX_JUNIT}"
        const val ANDROIDX_TEST_RUNNER = "androidx.test:runner:${Versions.ANDROIDX_TEST}"
        const val ANDROIDX_RULES = "androidx.test:rules:${Versions.ANDROIDX_TEST}"
        const val COMPOSE_UI_TEST = "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE}"
        const val COMPOSE_UI_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest:${Versions.COMPOSE}"
        const val COMPOSE_UI_TOOLING = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}"
    }
}