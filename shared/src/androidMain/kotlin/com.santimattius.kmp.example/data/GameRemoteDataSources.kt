package com.santimattius.kmp.example.data

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class AndroidGameRemoteDataSources() : GameRemoteDataSources {

    private val client = OkHttpClient().newBuilder()
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val services = retrofit.create<GameServices>()

    override suspend fun getGames(): Result<GameResponse> = runCatching {
        val games = services.getGames()
        val jsonElement = Json.parseToJsonElement(games)
        Json.decodeFromJsonElement<GameResponse>(jsonElement)
    }

    companion object {
        private const val baseUrl = "https://www.freetogame.com/api/"
    }
}