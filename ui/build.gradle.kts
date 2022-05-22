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

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = AppConfig.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = AppConfig.MIN_SDK_VERSION
        targetSdk = AppConfig.TARGET_SDK_VERSION
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions { jvmTarget = "1.8" }

    buildFeatures { compose = true }

    composeOptions { kotlinCompilerExtensionVersion = "1.1.1" }

    packagingOptions {
        resources.excludes.apply {
            add("META-INF/AL2.0")
            add("META-INF/LGPL2.1")
        }
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}

dependencies {
    implementation(project(":shell"))
    implementation(project(":acc"))
    implementation(Dependencies.Libs.APP_COMPAT)
    implementation(Dependencies.Libs.MATERIAL)
    implementation(Dependencies.Libs.CORE_KTX)
    implementation(Dependencies.Libs.LIFECYCLE)
    implementation(Dependencies.Libs.COMPOSE_UI)
    implementation(Dependencies.Libs.COMPOSE_MATERIAL3)
    implementation(Dependencies.Libs.COMPOSE_FOUNDATION)
    implementation(Dependencies.Libs.COMPOSE_TOOLING_PREVIEW)
    implementation(Dependencies.Libs.COMPOSE_CONSTRAINT_LAYOUT)
    implementation(Dependencies.Libs.HILT)
    implementation(Dependencies.Libs.WORK_MANAGER_RUNTIME)
    kapt(Dependencies.Libs.HILT_COMPILER)
    api(Dependencies.Libs.COMPOSE_ACTIVITY)
    kapt(Dependencies.Libs.HILT_ANDROID_COMPILER)
    debugImplementation(Dependencies.Libs.COMPOSE_UI_TOOLING)
    androidTestImplementation(Dependencies.Libs.JUNIT)
    androidTestImplementation(Dependencies.Libs.COMPOSE_UI_TEST)
    debugImplementation(Dependencies.Libs.COMPOSE_UI_TEST_MANIFEST)

    // TODO Remove these artifacts when compose preview bugs fixed.
    debugImplementation(Dependencies.Libs.CUSTOM_VIEW)
    debugImplementation(Dependencies.Libs.CUSTOM_VIEW_POOLING_ADAPTER)
}