package com.santimattius.kmp.unit.data.sources

import com.santimattius.kmp.data.NetworkCharacter
import com.santimattius.kmp.data.sources.CharacterNetworkDataSource

class StubCharacterNetworkDataSource(
    private val characters: MutableList<NetworkCharacter> = mutableListOf()
) : CharacterNetworkDataSource {

    fun setCharacters(characters: List<NetworkCharacter>) {
        this.characters.clear()
        this.characters.addAll(characters)
    }
    override suspend fun find(id: Long): Result<NetworkCharacter> {
        return all().fold(onSuccess = { characters ->
            runCatching { characters.first { it.id == id } }
        }, onFailure = { Result.failure(it) })
    }

    override suspend fun all(): Result<List<NetworkCharacter>> {
        return runCatching { characters }
    }
}