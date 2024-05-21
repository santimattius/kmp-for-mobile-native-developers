package com.santimattius.kmp.core

import com.santimattius.kmp.core.sources.CharacterLocalDataSource
import com.santimattius.kmp.core.sources.CharacterNetworkDataSource
import com.santimattius.kmp.domain.Character
import kotlinx.coroutines.flow.Flow

class CharacterRepository(
    private val local: CharacterLocalDataSource,
    private val network: CharacterNetworkDataSource,
) {

    val allCharacters: Flow<List<Character>>
        get() = local.all

    val allFavoritesCharacters: Flow<List<Character>>
        get() = local.favorites

    suspend fun fetch(): Result<Unit> {
        return network.all().fold(onSuccess = {
            it.forEach { network ->
                local.insert(network.asDomainModel())
            }
            Result.success(Unit)
        }, onFailure = {
            Result.failure(it)
        })
    }

    suspend fun findById(id: Long): Result<Character> {
        return local.find(id).fold(
            onSuccess = {
                Result.success(it)
            },
            onFailure = {
                network.find(id).fold(
                    onSuccess = { networkCharacter ->
                        val character = networkCharacter.asDomainModel()
                        local.insert(character)
                        Result.success(character)
                    },
                    onFailure = {
                        Result.failure(it)
                    }
                )
            }
        )

    }

    suspend fun addToFavorite(id: Long): Result<Unit> {
        return local.addToFavorite(id)
    }

    suspend fun removeFromFavorite(id: Long): Result<Unit> {
        return local.removeToFavorite(id)
    }
}