plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.serialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    api(libs.kotlinx.datetime)
    implementation(libs.junit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)
}