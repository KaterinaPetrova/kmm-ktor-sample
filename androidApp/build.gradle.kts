plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.android.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
}

android {
    defaultConfig {
        applicationId = "com.jetbrains.kmmktor2.android"
        compileSdk = (findProperty("android.compileSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        minSdk = (findProperty("android.minSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    namespace = "com.jetbrains.kmmktor2.android"
}