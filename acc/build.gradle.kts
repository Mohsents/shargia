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
        buildConfigField(
            "String",
            "ACC_VERSION_NAME",
            project.property("ACC_VERSION_NAME") as String
        )
        buildConfigField(
            "String",
            "ACC_VERSION_CODE",
            project.property("ACC_VERSION_CODE") as String
        )
        buildConfigField(
            "String",
            "ACC_FILE",
            project.property("ACC_FILE") as String
        )
        buildConfigField(
            "String",
            "ACC_INSTALL_SCRIPT",
            project.property("ACC_INSTALL_SCRIPT") as String
        )
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

    packagingOptions {
        resources.excludes.apply {
            add("META-INF/LICENSE")
            add("META-INF/*.properties")
            add("META-INF/AL2.0")
            add("META-INF/LGPL2.1")
        }
    }
}

dependencies {
    implementation(project(":shell"))
    api(project(":shared"))
    api(Dependencies.Libs.COROUTINE_CORE)
    implementation(Dependencies.Libs.ANNOTATION)
    implementation(Dependencies.Libs.HILT)
    androidTestImplementation(Dependencies.Libs.JUNIT)
    androidTestImplementation(Dependencies.Libs.HILT_ANDROID_TESTING)
    androidTestImplementation(Dependencies.Libs.ANDROIDX_JUNIT)
    androidTestImplementation(Dependencies.Libs.ANDROIDX_TEST_RUNNER)
    androidTestImplementation(Dependencies.Libs.ANDROIDX_RULES)
    kapt(Dependencies.Libs.HILT_ANDROID_COMPILER)
    kaptAndroidTest(Dependencies.Libs.HILT_ANDROID_COMPILER)
}