plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.kyi.knowyouringredients"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kyi.knowyouringredients"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
//            buildConfigField("String", "BASE_URL", "\"https://in.openfoodfacts.org/api/v2/\"")
            buildConfigField("String", "FOOD_BASE_URL", "\"https://in.openfoodfacts.net/\"")
            buildConfigField("String", "BEAUTY_BASE_URL", "\"https://in.openbeautyfacts.net/\"")
        }
        debug {
//            buildConfigField("String", "BASE_URL", "\"https://in.openfoodfacts.net/api/v2/\"")
            buildConfigField("String", "FOOD_BASE_URL", "\"https://in.openfoodfacts.net/\"")
            buildConfigField("String", "BEAUTY_BASE_URL", "\"https://in.openbeautyfacts.net/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    implementation(libs.coil.compose)
    implementation(libs.slf4j.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.ktor)
    implementation(libs.bundles.compose.core)
    implementation(libs.bundles.compose.material3)
    implementation(libs.bundles.compose.lifecycle)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui.text.google.fonts)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.core)
    // ML Kit for barcode scanning
    implementation(libs.barcode.scanning)
    // Accompanist Permissions for runtime permission handling
    implementation(libs.accompanist.permissions)
    // Room Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
}