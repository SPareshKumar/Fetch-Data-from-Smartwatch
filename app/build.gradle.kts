plugins {
    id("com.android.application") version "8.6.0" apply true
    id("org.jetbrains.kotlin.android") version "1.8.22" apply true
}

android {
    namespace = "com.example.healthmanager"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.healthmanager"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(libs.kotlin.stdlib) // Direct dependency reference
    implementation(libs.kotlin.stdlib.jdk7)
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.appcompat)
    implementation(libs.appcompat)
    implementation(libs.retrofit)
    // Converter for JSON
    implementation(libs.converter.gson)
    // Add other dependencies as needed
}



