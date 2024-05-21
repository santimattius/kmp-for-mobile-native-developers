package com.santimattius.kmp.unit.data.sources

import com.santimattius.kmp.core.sources.CharacterLocalDataSource
import com.santimattius.kmp.domain.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class InMemoryCharacterLocalDataSource : CharacterLocalDataSource {

    private val mutex = Mutex()

    private val _characters = mutableListOf<Character>()
    private val _favorites = mutableListOf<Character>()

    private val _flowAll = MutableStateFlow(_characters)
    private val _flowFavorites = MutableStateFlow(_favorites)
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
        _flowAll.update { _characters }
    }

    override suspend fun clear() = runCatching {
        _characters.clear()
        _favorites.clear()
        refresh()
    }

}