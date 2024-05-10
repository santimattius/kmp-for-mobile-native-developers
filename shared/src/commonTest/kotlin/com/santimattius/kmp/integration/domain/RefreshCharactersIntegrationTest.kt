package com.santimattius.kmp.integration.domain

import app.cash.turbine.test
import com.santimattius.kmp.JsonLoader
import com.santimattius.kmp.data.CharacterRepository
import com.santimattius.kmp.data.db.createDatabase
import com.santimattius.kmp.data.sources.ktor.KtorCharacterNetworkDataSource
import com.santimattius.kmp.data.sources.sqldelight.SQLDelightCharacterLocalDataSource
import com.santimattius.kmp.domain.RefreshCharacters
import com.santimattius.kmp.integration.data.db.testDbDriver
import com.santimattius.kmp.integration.data.network.DefaultMockResponse
import com.santimattius.kmp.integration.data.network.MockClient
import com.santimattius.kmp.integration.data.network.testKtorClient
import com.santimattius.kmp.unit.data.sources.InMemoryCharacterLocalDataSource
import io.kotest.common.runBlocking
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RefreshCharactersIntegrationTest {

    private val jsonResponse = JsonLoader.load("characters.json")

    //KtorClient setup
    private val mockClient = MockClient()
    private val ktorClient = testKtorClient(mockClient)
    private val networkDataSource = KtorCharacterNetworkDataSource(ktorClient)

    //SQLDelight setup
    private val db = createDatabase(driver = testDbDriver())
    private val localDataSource = SQLDelightCharacterLocalDataSource(db)
    private val repository = CharacterRepository(localDataSource, networkDataSource)

    private val refreshCharacters = RefreshCharacters(repository)

    @Test
    fun `When I call refresh update the local storage`() = runTest {
        //Given
        val response = DefaultMockResponse(jsonResponse, HttpStatusCode.OK)
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
        val response = DefaultMockResponse("{}", HttpStatusCode.OK)
        mockClient.setResponse(response)
        //When
        refreshCharacters.invoke()

        localDataSource.all.test {
            assertEquals(true, awaitItem().isEmpty())
        }
    }
}