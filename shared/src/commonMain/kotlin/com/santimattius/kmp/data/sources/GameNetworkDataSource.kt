package com.santimattius.kmp.data.sources

import com.santimattius.kmp.domain.Game

interface GameNetworkDataSource {
    suspend fun find(id: Long): Result<Game>
    suspend fun all(): Result<List<Game>>

}
