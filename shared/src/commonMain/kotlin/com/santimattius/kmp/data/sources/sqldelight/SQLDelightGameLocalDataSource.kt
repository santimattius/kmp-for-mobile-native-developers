package com.santimattius.kmp.data.sources.sqldelight

import com.santimattius.kmp.GameDatabase
import com.santimattius.kmp.data.sources.GameLocalDataSource
import com.santimattius.kmp.domain.Game
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal class SQLDelightGameLocalDataSource(
    db: GameDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : GameLocalDataSource {
    override suspend fun find(id: Long): Result<Game> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(game: Game): Result<Unit> {
        TODO("Not yet implemented")
    }
}