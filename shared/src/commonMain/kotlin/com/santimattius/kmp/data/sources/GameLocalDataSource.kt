package com.santimattius.kmp.data.sources

import com.santimattius.kmp.domain.Game

interface GameLocalDataSource {
    suspend fun find(id: Long): Result<Game>
    suspend fun insert(game: Game): Result<Unit>
}
