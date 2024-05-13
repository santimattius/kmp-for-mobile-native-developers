package com.santimattius.kmp

import android.content.Context
import com.santimattius.kmp.di.startKoinApplication
import org.koin.dsl.module

actual class PlatformConfig(val context: Context)

actual fun providePlatformInitializer(): PlatformInitializer {
    return object : PlatformInitializer {
        override fun init(config: PlatformConfig) {
            startKoinApplication(
                module {
                    single<Context> { config.context }
                }
            )
        }
    }
}
