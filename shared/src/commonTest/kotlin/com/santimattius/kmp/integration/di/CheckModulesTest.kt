package com.santimattius.kmp.integration.di

import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.Test

expect class CheckModulesTest : KoinTest {

    @AfterTest
    fun tearDown()

    @Test
    fun `validate modules`()
}