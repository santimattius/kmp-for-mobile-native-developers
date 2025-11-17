package com.santimattius.kmp.data

import com.santimattius.kmp.data.sources.CharacterLocalDataSource
import com.santimattius.kmp.data.sources.CharacterNetworkDataSource
import com.santimattius.kmp.domain.Character
import kotlinx.coroutines.flow.Flow

/**
 * CharacterRepository
 *
 * @property local, local data source
 * @property network, network data source
 */
class CharacterRepository(
    private val local: CharacterLocalDataSource,
    private val network: CharacterNetworkDataSource,
) {

    val allCharacters: Flow<List<Character>>
        get() = local.all


    val allFavoritesCharacters: Flow<List<Character>>
        get() = local.favorites

    /**
     * update local data source with network data source
     *
     * @return result of operation
     */
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

    /**
     * find character by id
     *
     * @param id, character id
     * @return result of operation with character
     */
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

    /**
     * add character to favorite
     *
     * @param id, character id
     * @return result of operation
     */
    suspend fun addToFavorite(id: Long): Result<Unit> {
        return local.addToFavorite(id)
    }

    /**
     * remove character from favorite
     *
     * @param id, character id
     * @return result of operation
     */
    suspend fun removeFromFavorite(id: Long): Result<Unit> {
        return local.removeToFavorite(id)
    }
}