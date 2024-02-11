package com.santimattius.kmp.data

import com.santimattius.kmp.data.sources.GameLocalDataSource
import com.santimattius.kmp.data.sources.GameNetworkDataSource
import com.santimattius.kmp.domain.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GameRepository(
    private val local: GameLocalDataSource,
    private val network: GameNetworkDataSource,
) {

    val allGames: Flow<List<Game>> = flowOf()

    suspend fun findById(id: Long): Result<Game> {
        return local.find(id).fold(
            onSuccess = {
                Result.success(it)
            },
            onFailure = {
                network.find(id).fold(
                    onSuccess = { game ->
                        local.insert(game)
                        Result.success(game)
                    },
                    onFailure = {
                        Result.failure(it)
                    }
                )
            }
        )
    }

    fun addToFavorite(id: Long): Result<Unit> {
        TODO("Not yet implemented")
    }

    fun removeFromFavorite(id: Long): Result<Unit> {
        TODO("Not yet implemented")
    }
}