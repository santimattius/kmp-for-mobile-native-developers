package com.santimattius.kmp.unit.domain

import app.cash.turbine.test
import com.santimattius.kmp.JsonLoader
import com.santimattius.kmp.core.CharacterRepository
import com.santimattius.kmp.core.CharactersResponse
import com.santimattius.kmp.domain.RefreshCharacters
import com.santimattius.kmp.unit.data.sources.InMemoryCharacterLocalDataSource
import com.santimattius.kmp.unit.data.sources.StubCharacterNetworkDataSource
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RefreshCharactersTest {

    private val characters = JsonLoader.load<CharactersResponse>("characters.json").results
    private val networkDataSource = StubCharacterNetworkDataSource(characters.toMutableList())
    private val localDataSource = InMemoryCharacterLocalDataSource()
    private val repository = CharacterRepository(localDataSource, networkDataSource)

    private val refreshCharacters = RefreshCharacters(repository)

    @Test
    fun `When I call refresh update the local storage`() = runTest {
        refreshCharacters.invoke()
        localDataSource.all.test {
            assertEquals(true, awaitItem().isNotEmpty())
        }
    }

    @Test
    fun `When the service returns an empty response`() = runTest {
        networkDataSource.setCharacters(emptyList())
        refreshCharacters.invoke()
        localDataSource.all.test {
            assertEquals(true, awaitItem().isEmpty())
        }
    }
}