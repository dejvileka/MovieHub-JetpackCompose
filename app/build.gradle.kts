plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization") version "2.0.20"

}

android {
    namespace = "com.dejvidLeka.app"
    compileSdk = 36

    defaultConfig {
        val apiKey: String = project.properties["API_KEY"] as? String ?: ""
        buildConfigField("String", "API_KEY", apiKey)
        testInstrumentationRunner = "com.dejvidleka.moviehub_jetpackcompose.CustomTestRunner"

        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.androidx.foundation)


    // UI
    implementation(libs.material)
    implementation(libs.androidx.material)
    implementation(libs.glide)

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.viewbinding)
    implementation(libs.androidx.constraintlayout.compose)

    debugImplementation(libs.androidx.compose.ui.tooling)

    // Accompanist
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.pager)

    implementation(libs.accompanist.pager.indicators)
    implementation(libs.accompanist.swiperefresh)


    // Image Loading
    implementation(platform(libs.coil.bom))
    implementation(libs.coil.compose)

    // Lifecycle and ViewModel
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.compose.runtime.livedata)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Navigation
    implementation(libs.androidx.navigation.compose)


    // Paging
    implementation(libs.androidx.paging.compose)

    // Room
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // WorkManager
    implementation(libs.androidx.work.runtime.ktx)

    // Networking
    implementation(libs.gson)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.okhttp.dnsoverhttps)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.retrofit2)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Profiling
    implementation(libs.androidx.profileinstaller)

    // Benchmarking
    implementation(libs.androidx.benchmark.common)

    // Logging
    implementation (libs.timber)

    // Fonts
    implementation(libs.androidx.ui.text.google.fonts)

    // Test Dependencies
    debugImplementation(libs.androidx.monitor)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.arch.core.testing)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.espresso.intents)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.uiautomator)
    androidTestImplementation(libs.androidx.work.testing)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.guava)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.accessibility.test.framework)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    kspAndroidTest(libs.hilt.android.compiler)
    testImplementation(libs.junit)
    implementation("androidx.compose.material:material-icons-extended:1.7.8")
}




