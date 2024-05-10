package com.santimattius.kmp.data.sources.sqldelight

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.santimattius.kmp.CharactersDatabase
import com.santimattius.kmp.data.asDomainModel
import com.santimattius.kmp.data.asDomainsModels
import com.santimattius.kmp.data.sources.CharacterLocalDataSource
import com.santimattius.kmp.domain.Character
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SQLDelightCharacterLocalDataSource(
    db: CharactersDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CharacterLocalDataSource {

    private val queries = db.charactersDatabaseQueries

    override val all: Flow<List<Character>>
        get() = queries.selectAllCharacter()
            .asFlow()
            .mapToList(dispatcher)
            .map {
                it.asDomainsModels()
            }

    override suspend fun find(id: Long): Result<Character> {
        return runCatching {
            val entity = queries.selectCharacterById(id).executeAsOne()
            entity.asDomainModel()
        }
    }

    override suspend fun addToFavorite(id: Long): Result<Unit> = runCatching {
        queries.updateFavorite(favorite = true, id = id)
    }

    override suspend fun removeToFavorite(id: Long): Result<Unit> = runCatching {
        queries.updateFavorite(favorite = false, id = id)
    }

    override suspend fun insert(character: Character): Result<Unit> = runCatching {
        val (id, title, image) = character
        queries.insertCharacter(id, title, image)
    }

    override suspend fun clear(): Result<Unit> = runCatching {
        queries.deleteAll()
    }
}