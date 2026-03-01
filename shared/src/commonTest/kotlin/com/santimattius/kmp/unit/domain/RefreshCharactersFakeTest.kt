package com.santimattius.kmp.unit.domain

import app.cash.turbine.test
import com.santimattius.kmp.JsonLoader
import com.santimattius.kmp.data.CharacterRepository
import com.santimattius.kmp.data.CharactersResponse
import com.santimattius.kmp.domain.RefreshCharacters
import com.santimattius.kmp.unit.data.sources.FakeCharacterNetworkDataSource
import com.santimattius.kmp.unit.data.sources.InMemoryCharacterLocalDataSource
import com.santimattius.kmp.unit.data.sources.StubCharacterNetworkDataSource
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * Manual stubbing strategy: uses [StubCharacterNetworkDataSource] (no mock framework).
 * Focuses on behavior and state; same scenarios as the Mokkery-based test for comparison.
 */
class RefreshCharactersFakeTest {

    private val networkDataSource = FakeCharacterNetworkDataSource()
    private val localDataSource = InMemoryCharacterLocalDataSource()
    private val repository = CharacterRepository(localDataSource, networkDataSource)

    private val refreshCharacters = RefreshCharacters(repository)

    @Test
    fun `given network returns characters when refreshCharacters is invoked then local storage is updated`() = runTest {
        // Given: stub network with characters (set up above)
        // When
        refreshCharacters.invoke()
        // Then
        localDataSource.all.test {
            assertTrue(awaitItem().isNotEmpty())
        }
    }

    @Test
    fun `given network returns empty list when refreshCharacters is invoked then local storage is empty`() = runTest {
        // Given
        networkDataSource.setResponse(Result.success(emptyList()))
        // When
        refreshCharacters.invoke()
        // Then
        localDataSource.all.test {
            assertTrue(awaitItem().isEmpty())
        }
    }
}
