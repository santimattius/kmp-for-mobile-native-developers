package com.santimattius.kmp.unit.viewmodels

import app.cash.turbine.test
import com.santimattius.kmp.data.CharacterRepository
import com.santimattius.kmp.domain.AddToFavorite
import com.santimattius.kmp.domain.GetAllCharacters
import com.santimattius.kmp.domain.RefreshCharacters
import com.santimattius.kmp.domain.RemoveFromFavorites
import com.santimattius.kmp.unit.data.sources.FakeCharacterNetworkDataSource
import com.santimattius.kmp.unit.data.sources.InMemoryCharacterLocalDataSource
import com.santimattius.kmp.viewmodels.CharactersViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersViewModelTest {

    private val networkDataSource = FakeCharacterNetworkDataSource()
    private val localDataSource = InMemoryCharacterLocalDataSource()
    private val repository = CharacterRepository(localDataSource, networkDataSource)
    private val getAllCharacters = GetAllCharacters(repository)
    private val refreshCharacters = RefreshCharacters(repository)
    private val addToFavorite = AddToFavorite(repository)
    private val removeFromFavorites = RemoveFromFavorites(repository)

    private val testDispatcher = UnconfinedTestDispatcher()

    private val viewModel = CharactersViewModel(
        getAllCharacters = getAllCharacters,
        refreshCharacters = refreshCharacters,
        addToFavorite = addToFavorite,
        removeFromFavorite = removeFromFavorites,
        viewModelScope = CoroutineScope(testDispatcher)
    )

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun firstTest() {
        runTest(testDispatcher) {
            viewModel.characters.test {
                assertEquals(true, awaitItem().isNotEmpty())
            }
        }
    }
}