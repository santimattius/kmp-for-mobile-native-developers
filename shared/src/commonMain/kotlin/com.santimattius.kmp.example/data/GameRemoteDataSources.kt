package com.santimattius.kmp.example.data

interface GameRemoteDataSources {

    suspend fun getGames(): Result<GameResponse>
}