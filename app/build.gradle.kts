import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.application")
    id("com.google.devtools.ksp")
//    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
}

kotlin {
    jvmToolchain(21)
}

android {
    namespace = "com.so.chat"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.so.chat"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        ndk {
            abiFilters += listOf("arm64-v8a")
        }
        vectorDrawables.useSupportLibrary = true
    }

    signingConfigs {
        create("release") {
            storeFile = file("sign.p12")
            storePassword = "8075"
            keyAlias = "sign"
            keyPassword = "8075"
            storeType = "pkcs12"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    // Compose BOM (keeps Compose library versions aligned)
    implementation(platform("androidx.compose:compose-bom:2025.10.00"))

    // Compose UI
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended:1.7.8")
    implementation("androidx.activity:activity-compose:1.12.0-alpha09")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.2.0-alpha02")

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.10.0-alpha05")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0-alpha05")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.9.5")

    // Room
    implementation("androidx.room:room-runtime:2.8.2")
    implementation("androidx.room:room-ktx:2.8.2")
    implementation("androidx.room:room-common-jvm:2.8.2")
    ksp("androidx.room:room-compiler:2.8.2")

    // Core
    implementation("androidx.core:core-ktx:1.17.0")

    // Koin
    implementation("io.insert-koin:koin-androidx-compose:4.1.2-Beta1")
    implementation("io.insert-koin:koin-androidx-compose-navigation:4.1.2-Beta1")

    // Kotlin Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
}