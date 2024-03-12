plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.googleDevtoolsKsp)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.kotlinx.coroutines)
    implementation(libs.arrow.core)
    implementation(libs.bundles.koinCore)
    ksp(libs.koin.ksp)
}
