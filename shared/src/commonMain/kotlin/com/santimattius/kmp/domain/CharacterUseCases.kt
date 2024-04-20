package com.santimattius.kmp.domain

import com.santimattius.kmp.data.CharacterRepository

class GetAllCharacters(
    private val repository: CharacterRepository,
) {

    operator fun invoke() = repository.allCharacters
}

class FindCharacterById(
    private val repository: CharacterRepository,
) {

    suspend operator fun invoke(id: Long): Result<Character> {
        return repository.findById(id)
    }
}

class RefreshCharacters(
    private val repository: CharacterRepository,
) {

    suspend operator fun invoke() = repository.fetch()
}