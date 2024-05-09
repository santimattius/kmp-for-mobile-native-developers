package com.santimattius.kmp.integration.domain

import app.cash.turbine.test
import com.santimattius.kmp.JsonLoader
import com.santimattius.kmp.data.CharacterRepository
import com.santimattius.kmp.data.CharactersResponse
import com.santimattius.kmp.data.sources.ktor.KtorCharacterNetworkDataSource
import com.santimattius.kmp.domain.RefreshCharacters
import com.santimattius.kmp.unit.data.sources.FakeCharacterNetworkDataSource
import com.santimattius.kmp.unit.data.sources.InMemoryCharacterLocalDataSource
import com.santimattius.kmp.unit.data.sources.StubCharacterNetworkDataSource
import dev.mokkery.mock
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RefreshCharactersIntegrationTest {

    private val jsonResponse = JsonLoader.load("characters.json")
    private val mockClient = MockClient()
    private val ktorClient = testKtorClient(mockClient)
    private val networkDataSource = KtorCharacterNetworkDataSource(ktorClient)

    private val localDataSource = InMemoryCharacterLocalDataSource()
    private val repository = CharacterRepository(localDataSource, networkDataSource)

    private val refreshCharacters = RefreshCharacters(repository)

    @Test
    fun `When I call refresh update the local storage`() = runTest {
        val response = DefaultMockResponse(jsonResponse, HttpStatusCode.OK)
        mockClient.intercept(response)
        refreshCharacters.invoke()
        localDataSource.all.test {
            assertEquals(true, awaitItem().isNotEmpty())
        }
    }

    @Test
    fun `When the service returns an empty response`() = runTest {
        val response = DefaultMockResponse("{}", HttpStatusCode.OK)
        mockClient.intercept(response)
        refreshCharacters.invoke()
        localDataSource.all.test {
            assertEquals(true, awaitItem().isEmpty())
        }
    }
}