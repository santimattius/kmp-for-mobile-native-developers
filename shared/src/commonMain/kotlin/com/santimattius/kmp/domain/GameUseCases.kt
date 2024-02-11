package com.santimattius.kmp.domain

import com.santimattius.kmp.data.GameRepository

class GetAllGames(
    private val repository: GameRepository,
) {

    operator fun invoke() = repository.allGames
}

class FindGameById(
    private val repository: GameRepository,
) {

    suspend operator fun invoke(id: Long): Result<Game> {
        return repository.findById(id)
    }
}