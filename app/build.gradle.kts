plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android)
    kotlin("kapt")
    alias(libs.plugins.google.ksp) // Add this
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
}

android {
    namespace = "com.rajan.ecommerce"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.rajan.ecommerce"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            cmake {
                cppFlags += "-Wl,-z,max-page-size=16384"
            }
        }
    }

    packaging {
        jniLibs {
            // This ensures libraries are stored uncompressed and aligned
            useLegacyPackaging = false
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Serialization (typed routes)
    implementation(libs.kotlinx.serialization.json)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.0")

    // Coroutines
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //  HILT (KAPT ONLY)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)

    // OkHttp
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.accompanist.systemuicontroller)
    //place autocomplete
    implementation(libs.google.places)
    //Coil for image
    implementation(libs.coil.compose)
    //Paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)

    implementation(libs.foundation.compose)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler) // Use ksp instead of kapt

    // Optional: Room Paging support if you use the Paging library
    implementation(libs.androidx.room.paging)

    // CameraX
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)

    // ML Kit Barcode Scanning
    implementation(libs.barcode.scanning)

    // Google Code Scanner (Easier for demo, doesn't require camera permissions logic)
    implementation(libs.play.services.code.scanner)

    implementation(platform("com.google.firebase:firebase-bom:34.11.0"))
    implementation("com.google.firebase:firebase-analytics")

    implementation(libs.play.services.maps)
    implementation(libs.compose.maps)
    implementation("com.razorpay:checkout:1.6.33")
    implementation("com.airbnb.android:lottie-compose:6.4.0")
}

kapt {
    correctErrorTypes = true
}
