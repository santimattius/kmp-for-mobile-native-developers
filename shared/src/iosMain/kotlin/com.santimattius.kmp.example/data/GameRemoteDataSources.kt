package com.santimattius.kmp.example.data

import kotlinx.serialization.json.Json

class IOSGameRemoteDataSources : GameRemoteDataSources {

    private val client = URLSessionClient()
    override suspend fun getGames(): Result<GameResponse> {
        return runCatching {
            val jsonString = client.fetch(baseUrl)
            Json.decodeFromString<GameResponse>(jsonString)
        }
    }


    companion object {
        private const val baseUrl = "https://www.freetogame.com/api/games"
    }
}
