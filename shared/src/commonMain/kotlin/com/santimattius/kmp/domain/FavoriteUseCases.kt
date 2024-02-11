package com.santimattius.kmp.domain

import com.santimattius.kmp.data.GameRepository

class AddToFavorite(
    private val repository: GameRepository,
) {

    suspend operator fun invoke(id: Long): Result<Unit> {
        return repository.addToFavorite(id)
    }
}


class RemoveFromFavorites(
    private val repository: GameRepository,
) {

    suspend operator fun invoke(id: Long): Result<Unit> {
        return repository.removeFromFavorite(id)
    }

}