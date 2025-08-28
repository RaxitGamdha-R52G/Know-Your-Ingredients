plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
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
            buildConfigField("String", "FOOD_BASE_URL", "\"https://in.openfoodfacts.org/\"")
            buildConfigField("String", "BEAUTY_BASE_URL", "\"https://in.openbeautyfacts.org/\"")
        }
        debug {
            buildConfigField("String", "FOOD_BASE_URL", "\"https://in.openfoodfacts.org/\"")
            buildConfigField("String", "BEAUTY_BASE_URL", "\"https://in.openbeautyfacts.org/\"")
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
    // Datastore
    implementation(libs.androidx.datastore.preferences)

    // Core Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.slf4j.android)


    // Compose
    implementation(libs.bundles.compose.core)
    implementation(libs.bundles.compose.material3)
    implementation(libs.bundles.compose.lifecycle)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui.text.google.fonts)
    // Coil Compose
    implementation(libs.coil.compose)


    // Dependency Injection Koin
    implementation(libs.bundles.koin)


    // Networking Ktor
    implementation(libs.bundles.ktor)


    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)



    // Camera and Barcode Scanning
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


    //Firebase BoM
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    // Firebase Auth
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.core)
    //Firebase FireStore
    implementation(libs.firebase.firestore)

}