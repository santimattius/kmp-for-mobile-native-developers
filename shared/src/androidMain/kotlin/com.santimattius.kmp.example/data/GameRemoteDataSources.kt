package com.santimattius.kmp.example.data

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class AndroidGameRemoteDataSources : GameRemoteDataSources {

    private val client = RetrofitClient(baseUrl)

    private val services = client.create<GameServices>()

    override suspend fun getGames(): Result<GameResponse> {
        return runCatching {
            val games = services.getGames()
            val jsonElement = Json.parseToJsonElement(games)
            Json.decodeFromJsonElement<GameResponse>(jsonElement)
        }
    }

    companion object {
        private const val baseUrl = "https://www.freetogame.com/api/"
    }
}