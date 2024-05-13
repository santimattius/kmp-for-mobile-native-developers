package com.santimattius.kmp.integration.di

import com.santimattius.kmp.di.sharedModule
import com.santimattius.kmp.di.startKoinApplication
import org.koin.core.KoinApplication
import org.koin.core.context.stopKoin
import org.koin.core.module.Module


fun startTestKoin(testModule: Module): KoinApplication {
    return startKoinApplication(listOf(testModule, sharedModule))
}

fun stopTestKoin() {
    stopKoin()
}