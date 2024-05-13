package com.santimattius.kmp

import com.santimattius.kmp.di.startKoinApplication

actual class PlatformConfig


actual fun providePlatformInitializer(): PlatformInitializer {
    return object : PlatformInitializer {
        override fun init(config: PlatformConfig) {
            startKoinApplication()
        }
    }
}