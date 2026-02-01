import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.skie)
    alias(libs.plugins.test.resources)
    alias(libs.plugins.mokkery)
    alias(libs.plugins.kover)
    alias(libs.plugins.dokka)
}

kotlin {
    applyDefaultHierarchyTemplate()

    androidLibrary {
        namespace = "com.santimattius.kmp.skeleton.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
        withHostTestBuilder { }.configure { }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation(libs.sqldelight.coroutines.extensions)

            implementation(libs.koin.core)

            implementation(libs.kotlinx.collections.immutable)
            implementation(libs.androidx.lifecycle.viewmodel)

        }

        commonTest.dependencies {
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
            implementation(libs.resource.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.turbine)

            implementation(libs.ktor.client.mock)

            implementation(libs.koin.test)
        }

        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.sqldelight.android.driver)
            implementation(libs.koin.android)
        }


        val androidTest = sourceSets.getByName("androidHostTest") {
            dependencies {
                implementation(libs.kotlin.test.junit5)
                implementation(libs.junit.jupiter)
                implementation(libs.junit.platform.launcher)
                implementation(libs.sqldelight.jvm)
            }
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqldelight.ios.driver)

        }
    }
}

sqldelight {
    databases {
        create("CharactersDatabase") {
            packageName.set("com.santimattius.kmp")
        }
    }

    linkSqlite.set(true)

}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    failOnNoDiscoveredTests = false
    // Filter with Gradle: --tests "com.santimattius.kmp.**" (use ** for subpackages)
}

kover {
    reports{
        verify {
            rule("Basic Line Coverage") {
                bound {
                    minValue = 80 // Minimum coverage percentage
                    maxValue = 100 // Maximum coverage percentage (optional)
                }
            }

            rule("Branch Coverage") {
                bound {
                    minValue = 70 // Minimum coverage percentage for branches
                }
            }
        }
    }
}
