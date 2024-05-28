plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.sqldelight) apply false
    alias(libs.plugins.skie) apply false
    alias(libs.plugins.test.resources) apply false
    alias(libs.plugins.kotest) apply false
    alias(libs.plugins.kover) apply false
    alias(libs.plugins.dokka) apply false
}