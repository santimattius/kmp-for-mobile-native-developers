package com.santimattius.kmp.data.sources.ktor

import com.santimattius.kmp.data.sources.GameNetworkDataSource
import com.santimattius.kmp.domain.Game
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal class KtorGameNetworkDataSource(
    private val client: HttpClient,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : GameNetworkDataSource {
    override suspend fun find(id: Long): Result<Game> {
        TODO("Not yet implemented")
    }

    override suspend fun all(): Result<List<Game>> {
        TODO("Not yet implemented")
    }
}