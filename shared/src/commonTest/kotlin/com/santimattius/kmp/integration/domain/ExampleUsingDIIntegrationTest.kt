package com.santimattius.kmp.integration.domain

import app.cash.turbine.test
import com.santimattius.kmp.JsonLoader
import com.santimattius.kmp.core.sources.CharacterLocalDataSource
import com.santimattius.kmp.di.DataBases
import com.santimattius.kmp.di.sharedModule
import com.santimattius.kmp.domain.RefreshCharacters
import com.santimattius.kmp.integration.data.network.MockClient
import com.santimattius.kmp.integration.data.network.MockResponse
import com.santimattius.kmp.integration.di.stopTestKoin
import com.santimattius.kmp.integration.di.testPlatformModule
import kotlinx.coroutines.test.runTest
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

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
    fun `When I call refresh update the local storage`() = runTest {
        //Given
        val useCase = get<RefreshCharacters>()
        val localDataSource = get<CharacterLocalDataSource>(named(DataBases.SQLDELIGHT))
        val response = MockResponse.ok(jsonResponse)
        mockClient.setResponse(response)
        //When
        useCase.invoke()
        //Then
        localDataSource.all.test {
            assertEquals(true, awaitItem().isNotEmpty())
        }
    }

    @Test
    fun `When the service returns an empty response`() = runTest {
        //Given
        val useCase = get<RefreshCharacters>()
        val localDataSource = get<CharacterLocalDataSource>(named(DataBases.SQLDELIGHT))
        val response = MockResponse.default()
        mockClient.setResponse(response)
        //When
        useCase.invoke()

        localDataSource.all.test {
            assertEquals(true, awaitItem().isEmpty())
        }
    }
}