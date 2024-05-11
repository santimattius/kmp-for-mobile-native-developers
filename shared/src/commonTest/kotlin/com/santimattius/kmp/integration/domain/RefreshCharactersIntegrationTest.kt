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
import kotlin.test.assertEquals

class RefreshCharactersIntegrationTest {

    private val jsonResponse = JsonLoader.load("characters.json")

    //KtorClient setup
    private val mockClient = MockClient()
    private val ktorClient = testKtorClient(mockClient)
    private val networkDataSource = KtorCharacterNetworkDataSource(ktorClient)

    //SQLDelight setup
    private val sqlDriver = testDbDriver()
    private val db = createDatabase(driver = sqlDriver)
    private val localDataSource = SQLDelightCharacterLocalDataSource(db)
    private val repository = CharacterRepository(localDataSource, networkDataSource)

    private val refreshCharacters = RefreshCharacters(repository)

    @Test
    fun `When I call refresh update the local storage`() = runTest {
        //Given
        val response = MockResponse.ok(jsonResponse)
        mockClient.setResponse(response)
        //When
        refreshCharacters.invoke()
        //Then
        localDataSource.all.test {
            assertEquals(true, awaitItem().isNotEmpty())
        }
    }

    @Test
    fun `When the service returns an empty response`() = runTest {
        //Given
        val response = MockResponse.default()
        mockClient.setResponse(response)
        //When
        refreshCharacters.invoke()

        localDataSource.all.test {
            assertEquals(true, awaitItem().isEmpty())
        }
    }
}