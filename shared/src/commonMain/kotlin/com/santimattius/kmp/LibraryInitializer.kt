package com.santimattius.kmp

class LibraryInitializer {

    private val platformInitializer = providePlatformInitializer()

    fun init(config: PlatformConfig) {
        platformInitializer.init(config)
    }
}


interface PlatformInitializer {
    fun init(config: PlatformConfig)
}


expect fun providePlatformInitializer(): PlatformInitializer
expect class PlatformConfig
