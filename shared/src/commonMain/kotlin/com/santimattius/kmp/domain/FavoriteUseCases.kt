package com.santimattius.kmp.domain

import com.santimattius.kmp.data.CharacterRepository

class AddToFavorite(
    private val repository: CharacterRepository,
) {

    suspend operator fun invoke(id: Long): Result<Unit> {
        return repository.addToFavorite(id)
    }
}


class RemoveFromFavorites(
    private val repository: CharacterRepository,
) {

    suspend operator fun invoke(id: Long): Result<Unit> {
        return repository.removeFromFavorite(id)
    }

}