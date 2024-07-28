plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.serialization)
    alias(libs.plugins.google.ksp)
    kotlin("kapt")
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    implementation(libs.kotlinx.serialization.json)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
}