package com.santimattius.kmp.unit.data.sources

import com.santimattius.kmp.data.sources.CharacterLocalDataSource
import com.santimattius.kmp.domain.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class InMemoryCharacterLocalDataSource : CharacterLocalDataSource {

    private val mutex = Mutex()

    private val characters = mutableListOf<Character>()
    private val favorites = mutableListOf<Character>()

    private val _flow = MutableStateFlow(characters)
    override val all: Flow<List<Character>>
        get() = _flow

    override suspend fun find(id: Long): Result<Character> {
        return runCatching {
            mutex.withLock { characters.first { it.id == id } }
        }
    }

    override suspend fun addToFavorite(id: Long): Result<Unit> {
        return find(id).fold(
            onSuccess = {
                favorites.add(it)
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
                favorites.remove(it)
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
                characters.add(character)
                refresh()
            }
        }
    }

    private fun refresh() {
        _flow.update { characters }
    }

    fun clear() {
        characters.clear()
        favorites.clear()
    }

}