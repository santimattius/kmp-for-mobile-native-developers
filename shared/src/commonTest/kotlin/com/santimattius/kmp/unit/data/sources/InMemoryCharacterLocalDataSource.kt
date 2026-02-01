package com.santimattius.kmp.unit.data.sources

import com.santimattius.kmp.data.sources.CharacterLocalDataSource
import com.santimattius.kmp.domain.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * In-memory [CharacterLocalDataSource] for unit tests.
 *
 * [refresh] updates both [all] and [favorites] flows so that collectors see favorite changes
 * (add/remove) and list updates (insert). Use with ViewModel tests that assert on flow emissions.
 */
class InMemoryCharacterLocalDataSource : CharacterLocalDataSource {

    private val mutex = Mutex()

    private val _characters = mutableListOf<Character>()
    private val _favorites = mutableListOf<Character>()

    private val _flowAll = MutableStateFlow<List<Character>>(emptyList())
    private val _flowFavorites = MutableStateFlow<List<Character>>(emptyList())
    override val all: Flow<List<Character>>
        get() = _flowAll
    override val favorites: Flow<List<Character>>
        get() = _flowFavorites

    override suspend fun find(id: Long): Result<Character> {
        return runCatching {
            mutex.withLock { _characters.first { it.id == id } }
        }
    }

    override suspend fun addToFavorite(id: Long): Result<Unit> {
        return find(id).fold(
            onSuccess = {
                _favorites.add(it)
                val index = _characters.indexOfFirst { c -> c.id == id }
                if (index >= 0) _characters[index] = it.copy(isFavorite = true)
                refresh()
                Result.success(Unit)
            }, onFailure = {
                Result.failure(it)
            }
        )
    }

    override suspend fun removeToFavorite(id: Long): Result<Unit> {
        return find(id).fold(
            onSuccess = {
                _favorites.remove(it)
                val index = _characters.indexOfFirst { c -> c.id == id }
                if (index >= 0) _characters[index] = it.copy(isFavorite = false)
                refresh()
                Result.success(Unit)
            }, onFailure = {
                Result.failure(it)
            }
        )
    }

    override suspend fun insert(character: Character): Result<Unit> {
        return runCatching {
            mutex.withLock {
                _characters.add(character)
                refresh()
            }
        }
    }

    private fun refresh() {
        _flowAll.value = _characters.toList()
        _flowFavorites.value = _favorites.toList()
    }

    override suspend fun clear() = runCatching {
        _characters.clear()
        _favorites.clear()
        refresh()
    }

}