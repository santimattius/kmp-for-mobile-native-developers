package com.santimattius.kmp.integration.di

import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import kotlin.test.AfterTest
import kotlin.test.Test

class CheckModulesTest : KoinTest {

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `given test and shared modules when Koin is started then all module definitions resolve successfully`() {
        // Given: testPlatformModule + sharedModule
        // When
        startTestKoin(testPlatformModule)
            .checkModules()
        // Then: no unresolved dependencies (checkModules throws on failure)
    }
}