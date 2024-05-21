package com.santimattius.kmp.core.sources.ktor

import com.santimattius.kmp.core.CharactersResponse
import com.santimattius.kmp.core.NetworkCharacter
import com.santimattius.kmp.core.sources.CharacterNetworkDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class KtorCharacterNetworkDataSource(
    private val client: HttpClient,
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