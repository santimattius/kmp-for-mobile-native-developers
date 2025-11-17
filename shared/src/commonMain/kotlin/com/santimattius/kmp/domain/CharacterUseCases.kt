package com.santimattius.kmp.domain

import com.santimattius.kmp.data.CharacterRepository
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.map

class GetAllCharacters(
    private val repository: CharacterRepository,
) {

    operator fun invoke() = repository.allCharacters.map {
        it.toImmutableList()
    }
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