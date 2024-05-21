package com.santimattius.kmp.core.sources

import com.santimattius.kmp.core.db.CharacterDao
import com.santimattius.kmp.core.toCharacter
import com.santimattius.kmp.core.toLocalCharacter
import com.santimattius.kmp.core.toCharacters
import com.santimattius.kmp.domain.Character
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RoomCharacterLocalDataSource(
    private val characterDao: CharacterDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CharacterLocalDataSource {
    override val all: Flow<List<Character>>
        get() = characterDao.getAll()
            .map { it.toCharacters() }
            .flowOn(dispatcher)
    override val favorites: Flow<List<Character>>
        get() = characterDao.getFavorite()
            .map { it.toCharacters() }
            .flowOn(dispatcher)

    override suspend fun find(id: Long): Result<Character> = runSafe {
        characterDao.findById(id).toCharacter()
    }

    override suspend fun addToFavorite(id: Long): Result<Unit> = runSafe {
        characterDao.updateFavorite(id, true)
    }

    override suspend fun removeToFavorite(id: Long): Result<Unit> = runSafe {
        characterDao.updateFavorite(id, false)
    }

    override suspend fun insert(character: Character): Result<Unit> = runSafe {
        characterDao.insert(character.toLocalCharacter())
    }

    override suspend fun clear(): Result<Unit> = runSafe {
        characterDao.deleteAll()
    }

    private suspend fun <T> runSafe(block: suspend () -> T): Result<T> {
        return runCatching {
            withContext(dispatcher) { block() }
        }
    }
}



