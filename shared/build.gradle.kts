import kotlinx.kover.gradle.plugin.dsl.AggregationType
import kotlinx.kover.gradle.plugin.dsl.MetricType

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.skie)
    alias(libs.plugins.test.resources)
    alias(libs.plugins.kotest)
    alias(libs.plugins.mokkery)
    alias(libs.plugins.kover)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
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

    targets.all {
        compilations.all {
            kotlinOptions {
                verbose = true
            }
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

            implementation(libs.androidx.lifecycle.viewmodel)

        }

        commonTest.dependencies {
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
            implementation(libs.resource.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.turbine)
            implementation(libs.kotest.framework.engine)

            implementation(libs.ktor.client.mock)

            implementation(libs.koin.test)
        }

        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.sqldelight.android.driver)
            implementation(libs.koin.android)
        }


        val androidTest = sourceSets.getByName("androidUnitTest") {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(libs.junit)
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

koverReport {
    verify {
        rule("Basic Line Coverage") {
            isEnabled = true
            bound {
                minValue = 80 // Minimum coverage percentage
                maxValue = 100 // Maximum coverage percentage (optional)
                metric = MetricType.LINE
                aggregation = AggregationType.COVERED_PERCENTAGE
            }
        }

        rule("Branch Coverage") {
            isEnabled = true
            bound {
                minValue = 70 // Minimum coverage percentage for branches
                metric = MetricType.BRANCH
            }
        }
    }
}

android {
    namespace = "com.santimattius.kmp.skeleton.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
