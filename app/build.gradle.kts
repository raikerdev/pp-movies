plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.jetbrainsKotlinKapt)
    alias(libs.plugins.googleDevtoolsKsp)
    alias(libs.plugins.jetbrainsKotlinParcelize)
    alias(libs.plugins.androidxNavigation)
    alias(libs.plugins.googleDaggerHilt)
}

android {
    namespace = "com.raikerdev.petproject.movies"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.raikerdev.petproject.movies"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.raikerdev.petproject.movies.di.HiltTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":usecases"))
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(libs.bundles.core)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.retrofit)
    implementation(libs.google.plaServices)
    implementation(libs.glide.core)
    implementation(libs.arrow.core)
    implementation(libs.bundles.room)
    implementation(libs.hilt.android)
    testImplementation(project(":appTestShared"))
    ksp(libs.bundles.compiler)

    testImplementation(libs.bundles.unitTest)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)

    androidTestImplementation(project(":appTestShared"))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.okhttp3.mockWebServer)
    kspAndroidTest(libs.hilt.compiler)

}
