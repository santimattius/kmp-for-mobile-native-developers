package com.santimattius.kmp.example.data

import retrofit2.http.GET

interface GameServices {

    @GET("game")
    suspend fun getGames(): String
}