package com.santimattius.kmp

import android.content.Context
import com.santimattius.kmp.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

actual class PlatformConfig(val context: Context)

actual fun providePlatformInitializer(): PlatformInitializer {
    return object : PlatformInitializer {
        override fun init(config: PlatformConfig) {
            startKoin {
                androidContext(config.context.applicationContext)
                modules(platformModule + sharedModule)
            }
        }
    }
}
