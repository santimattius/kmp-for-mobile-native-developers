package com.santimattius.kmp.unit.domain.mokkery

import com.santimattius.kmp.data.CharacterRepository
import com.santimattius.kmp.unit.data.network.CharactersResponseMother
import com.santimattius.kmp.data.sources.CharacterNetworkDataSource
import com.santimattius.kmp.unit.data.sources.InMemoryCharacterLocalDataSource
import com.santimattius.kmp.domain.RefreshCharacters
import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verify.VerifyMode.Companion.exactly
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RefreshCharactersTest {

    private val characters = CharactersResponseMother.characters()
    private val networkDataSource = mock<CharacterNetworkDataSource>()
    private val localDataSource = InMemoryCharacterLocalDataSource()
    private val repository = CharacterRepository(localDataSource, networkDataSource)

    private val refreshCharacters = RefreshCharacters(repository)

    @Test
    fun `When I call refresh update the local storage`() = runTest {
        //Given
        everySuspend {
            networkDataSource.all()
        } returns Result.success(characters)
        //When
        refreshCharacters.invoke()
        //Then
        verifySuspend(mode = exactly(1)) {
            networkDataSource.all()
        }
        localDataSource.all.test {
            assertEquals(true, awaitItem().isNotEmpty())
        }
    }

    @Test
    fun `When the service returns an empty response`() = runTest {
        //Given
        everySuspend {
            networkDataSource.all()
        } returns Result.success(emptyList())
        //When
        refreshCharacters.invoke()
        //Then
        localDataSource.all.test {
            assertEquals(true, awaitItem().isEmpty())
        }
    }

    @Test
    fun `When the service returns an failure response`() = runTest {
        //Given
        everySuspend {
            networkDataSource.all()
        } returns Result.failure(Throwable("Testing failure response"))
        //When
        refreshCharacters.invoke().onFailure {
            assertEquals("Testing failure response", it.message)
        }
    }
}