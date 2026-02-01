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
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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
    fun `given default fakes when ViewModel is created then characters flow emits non empty list`() {
        runTest(testDispatcher) {
            // Given: ViewModel with FakeCharacterNetworkDataSource (characters.json)
            // When: init triggers refresh
            viewModel.characters.test {
                // Then (first emission is loaded list after init/refresh)
                assertTrue(awaitItem().isNotEmpty())
            }
        }
    }

    @Test
    fun `given refresh succeeds when ViewModel init runs then characters flow reflects data from repository`() {
        runTest(testDispatcher) {
            // Given: ViewModel with fakes; init triggers refresh
            viewModel.characters.test {
                // When: we collect after init/refresh
                val list = awaitItem()
                // Then: flow reflects repository data (non-empty, from fake)
                assertTrue(list.isNotEmpty())
                assertTrue(list.first().name.isNotEmpty())
            }
        }
    }

    @Test
    fun `given user toggles favorite when addToFavorites is called then character isFavorite flips and flow updates`() {
        runTest(testDispatcher) {
            // Given: ViewModel with data; pick a character that is not favorite
            viewModel.characters.test {
                try {
                    val list = awaitItem()
                    assertTrue(list.isNotEmpty())
                    val character = list.first()
                    assertFalse(character.isFavorite)
                    // When
                    viewModel.addToFavorites(character)
                    // Then: flow emits updated list with character marked favorite
                    val updatedList = awaitItem()
                    val updated = updatedList.first { it.id == character.id }
                    assertTrue(updated.isFavorite)
                } finally {
                    cancelAndIgnoreRemainingEvents()
                }
            }
        }
    }
}