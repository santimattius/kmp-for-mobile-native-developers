package com.santimattius.kmp.data.sources

import com.santimattius.kmp.domain.Character
import kotlinx.coroutines.flow.Flow

interface CharacterLocalDataSource {

    val all: Flow<List<Character>>
    suspend fun find(id: Long): Result<Character>
    suspend fun addToFavorite(id: Long): Result<Unit>
    suspend fun removeToFavorite(id: Long): Result<Unit>
    suspend fun insert(character: Character): Result<Unit>
    suspend fun clear(): Result<Unit>
}
