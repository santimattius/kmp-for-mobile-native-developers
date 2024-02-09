package com.santimattius.kmp.example.data

class GameRepository(
    private val remoteDataSources: GameRemoteDataSources = provideGameDataSource(),
) {

    suspend fun fetch(): Result<GameResponse> {
        return remoteDataSources.getGames()
    }
}