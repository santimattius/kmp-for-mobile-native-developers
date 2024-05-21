package com.santimattius.kmp.integration.di

import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import kotlin.test.AfterTest
import kotlin.test.Ignore
import kotlin.test.Test

class CheckModulesTest : KoinTest {

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    @Ignore
    fun `validate modules`() {
        startTestKoin(testPlatformModule)
            .checkModules()
    }
}