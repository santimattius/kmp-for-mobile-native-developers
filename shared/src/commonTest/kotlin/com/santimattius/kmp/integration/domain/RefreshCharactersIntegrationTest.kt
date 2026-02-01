package com.santimattius.kmp.integration.domain

import app.cash.turbine.test
import com.santimattius.kmp.JsonLoader
import com.santimattius.kmp.data.CharacterRepository
import com.santimattius.kmp.data.db.createDatabase
import com.santimattius.kmp.data.sources.ktor.KtorCharacterNetworkDataSource
import com.santimattius.kmp.data.sources.sqldelight.SQLDelightCharacterLocalDataSource
import com.santimattius.kmp.domain.RefreshCharacters
import com.santimattius.kmp.integration.data.db.testDbDriver
import com.santimattius.kmp.integration.data.network.MockClient
import com.santimattius.kmp.integration.data.network.MockResponse
import com.santimattius.kmp.integration.data.network.testKtorClient
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class RefreshCharactersIntegrationTest {

    private val jsonResponse = JsonLoader.load("characters.json")

    // KtorClient setup
    private val mockClient = MockClient()
    private val ktorClient = testKtorClient(mockClient)
    private val networkDataSource = KtorCharacterNetworkDataSource(ktorClient)

    // SQLDelight setup
    private val sqlDriver = testDbDriver()
    private val db = createDatabase(driver = sqlDriver)
    private val localDataSource = SQLDelightCharacterLocalDataSource(db)
    private val repository = CharacterRepository(localDataSource, networkDataSource)

    private val refreshCharacters = RefreshCharacters(repository)

    @Test
    fun `given mock returns characters JSON when refreshCharacters is invoked then local storage is updated`() = runTest {
        // Given
        val response = MockResponse.ok(jsonResponse)
        mockClient.setResponse(response)
        // When
        refreshCharacters.invoke()
        // Then
        localDataSource.all.test {
            assertTrue(awaitItem().isNotEmpty())
        }
    }

    @Test
    fun `given mock returns empty response when refreshCharacters is invoked then local storage is empty`() = runTest {
        // Given
        val response = MockResponse.default()
        mockClient.setResponse(response)
        // When
        refreshCharacters.invoke()
        // Then
        localDataSource.all.test {
            assertTrue(awaitItem().isEmpty())
        }
    }
}