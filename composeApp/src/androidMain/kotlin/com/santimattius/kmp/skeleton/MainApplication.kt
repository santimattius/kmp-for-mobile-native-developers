package com.santimattius.kmp.skeleton

import android.app.Application
import com.santimattius.kmp.LibraryInitializer
import com.santimattius.kmp.PlatformConfig

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LibraryInitializer().init(config = PlatformConfig(this))
    }
}