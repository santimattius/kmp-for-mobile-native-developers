package com.santimattius.kmp.unit.domain.mokkery

import app.cash.turbine.test
import com.santimattius.kmp.data.CharacterRepository
import com.santimattius.kmp.data.sources.CharacterNetworkDataSource
import com.santimattius.kmp.domain.RefreshCharacters
import com.santimattius.kmp.unit.data.network.CharactersResponseMother
import com.santimattius.kmp.unit.data.sources.InMemoryCharacterLocalDataSource
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode.Companion.exactly
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Mokkery implementation: uses Mokkery mocks and [verifySuspend] for interaction verification.
 * Same scenarios as the stub-based test; demonstrates mock-based strategy vs manual stubs.
 */
class RefreshCharactersMokkeryTest {

    private val characters = CharactersResponseMother.characters()
    private val networkDataSource = mock<CharacterNetworkDataSource>()
    private val localDataSource = InMemoryCharacterLocalDataSource()
    private val repository = CharacterRepository(localDataSource, networkDataSource)

    private val refreshCharacters = RefreshCharacters(repository)

    @Test
    fun `given network returns characters when refreshCharacters is invoked then local storage is updated`() = runTest {
        // Given
        everySuspend {
            networkDataSource.all()
        } returns Result.success(characters)
        // When
        refreshCharacters.invoke()
        // Then
        verifySuspend(mode = exactly(1)) {
            networkDataSource.all()
        }
        localDataSource.all.test {
            assertTrue(awaitItem().isNotEmpty())
        }
    }

    @Test
    fun `given network returns empty list when refreshCharacters is invoked then local storage is empty`() = runTest {
        // Given
        everySuspend {
            networkDataSource.all()
        } returns Result.success(emptyList())
        // When
        refreshCharacters.invoke()
        // Then
        localDataSource.all.test {
            assertTrue(awaitItem().isEmpty())
        }
    }

    @Test
    fun `given network returns failure when refreshCharacters is invoked then result is failure`() = runTest {
        // Given
        everySuspend {
            networkDataSource.all()
        } returns Result.failure(Throwable("Testing failure response"))
        // When
        refreshCharacters.invoke().onFailure {
            // Then
            assertEquals("Testing failure response", it.message)
        }
    }
}
