package com.santimattius.kmp.integration.di

import com.santimattius.kmp.di.sharedModule
import com.santimattius.kmp.di.startKoinApplication
import org.koin.core.KoinApplication
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

/**
 * Test Koin lifecycle. Convention: always call [stopTestKoin] in `@AfterTest` so Koin is torn down
 * after every test (even on failure). Start Koin in `@BeforeTest` or inside the test method.
 */
fun startTestKoin(testModule: Module): KoinApplication {
    return startKoinApplication(listOf(testModule, sharedModule))
}

fun stopTestKoin() {
    stopKoin()
}