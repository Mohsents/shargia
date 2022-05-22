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
        testInstrumentationRunner = "com.mohsents.shared.test.HiltTestRunner"
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
}

dependencies {
    api(Dependencies.Libs.COROUTINES_TEST)
    api(Dependencies.Libs.DATASTORE)
    api(Dependencies.Libs.TIMBER)
    api(Dependencies.Libs.HILT_WORK_MANAGER)
    implementation(Dependencies.Libs.HILT)
    implementation(Dependencies.Libs.COROUTINE_CORE)
    implementation(Dependencies.Libs.HILT_ANDROID_TESTING)
    implementation(Dependencies.Libs.JUNIT)
    implementation(Dependencies.Libs.ANDROIDX_TEST_RUNNER)
    implementation(Dependencies.Libs.ANDROIDX_JUNIT)
    kaptAndroidTest(Dependencies.Libs.HILT_ANDROID_COMPILER)
    kapt(Dependencies.Libs.HILT_ANDROID_COMPILER)
}