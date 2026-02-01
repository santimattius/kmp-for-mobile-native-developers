package com.santimattius.kmp.integration.domain

import app.cash.turbine.test
import com.santimattius.kmp.JsonLoader
import com.santimattius.kmp.data.sources.CharacterLocalDataSource
import com.santimattius.kmp.di.sharedModule
import com.santimattius.kmp.domain.RefreshCharacters
import com.santimattius.kmp.integration.data.network.MockClient
import com.santimattius.kmp.integration.data.network.MockResponse
import com.santimattius.kmp.integration.di.stopTestKoin
import com.santimattius.kmp.integration.di.testPlatformModule
import kotlinx.coroutines.test.runTest
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class ExampleUsingDIIntegrationTest : KoinTest {

    private val jsonResponse = JsonLoader.load("characters.json")

    private val mockClient: MockClient by inject()

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                testPlatformModule,
                sharedModule
            )
        }
    }

    @AfterTest
    fun tearDown() {
        stopTestKoin()
    }

    @Test
    fun `given Koin and mock returns characters when RefreshCharacters is invoked then local storage is updated`() = runTest {
        // Given
        val useCase = get<RefreshCharacters>()
        val localDataSource = get<CharacterLocalDataSource>()
        val response = MockResponse.ok(jsonResponse)
        mockClient.setResponse(response)
        // When
        useCase.invoke()
        // Then
        localDataSource.all.test {
            assertTrue(awaitItem().isNotEmpty())
        }
    }

    @Test
    fun `given Koin and mock returns empty when RefreshCharacters is invoked then local storage is empty`() = runTest {
        // Given
        val useCase = get<RefreshCharacters>()
        val localDataSource = get<CharacterLocalDataSource>()
        val response = MockResponse.default()
        mockClient.setResponse(response)
        // When
        useCase.invoke()
        // Then
        localDataSource.all.test {
            assertTrue(awaitItem().isEmpty())
        }
    }
}