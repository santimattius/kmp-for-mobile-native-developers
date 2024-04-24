package com.santimattius.kmp.data.sources.ktor

import com.santimattius.kmp.data.CharactersResponse
import com.santimattius.kmp.data.NetworkCharacter
import com.santimattius.kmp.data.sources.CharacterNetworkDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal class KtorCharacterNetworkDataSource(
    private val client: HttpClient,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CharacterNetworkDataSource {
    override suspend fun find(id: Long): Result<NetworkCharacter> {
        return runCatching {
            client.get("/api/character/${id}").body<NetworkCharacter>()
        }
    }

    override suspend fun all(): Result<List<NetworkCharacter>> {
        return runCatching {
            val response = client.get("/api/character").body<CharactersResponse>()
            response.results
        }
    }
}