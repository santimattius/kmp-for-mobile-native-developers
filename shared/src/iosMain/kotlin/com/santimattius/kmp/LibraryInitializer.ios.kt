package com.santimattius.kmp

import com.santimattius.kmp.di.sharedModule
import org.koin.core.context.startKoin

actual class PlatformConfig


actual fun providePlatformInitializer(): PlatformInitializer {
    return object : PlatformInitializer {
        override fun init(config: PlatformConfig) {
            startKoin {
                modules(platformModule + sharedModule)
            }
        }
    }
}