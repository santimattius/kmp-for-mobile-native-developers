package com.santimattius.kmp.integration.di

import androidx.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.robolectric.annotation.SQLiteMode
import kotlin.test.AfterTest
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
@SQLiteMode(SQLiteMode.Mode.NATIVE)
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