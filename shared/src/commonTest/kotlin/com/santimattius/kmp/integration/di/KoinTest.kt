package com.santimattius.kmp.integration.di

import com.santimattius.kmp.di.startKoinApplication
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import kotlin.test.AfterTest
import kotlin.test.Test

class KoinTest : KoinTest {

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun test() {
        startTestKoin(testPlatformModule)
            .checkModules()
    }
}