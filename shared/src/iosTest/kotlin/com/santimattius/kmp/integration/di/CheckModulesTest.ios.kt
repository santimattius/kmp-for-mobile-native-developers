package com.santimattius.kmp.integration.di

import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import kotlin.test.AfterTest
import kotlin.test.Test

actual class CheckModulesTest : KoinTest {
    @AfterTest
    actual fun tearDown() {
        stopTestKoin()
    }

    @Test
    actual fun `validate modules`() {
        startTestKoin(testPlatformModule)
            .checkModules()
    }

}