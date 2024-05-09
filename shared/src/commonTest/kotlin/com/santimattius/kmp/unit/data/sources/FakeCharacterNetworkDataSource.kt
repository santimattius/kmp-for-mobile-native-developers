package com.santimattius.kmp.unit.data.sources

import com.santimattius.kmp.JsonLoader
import com.santimattius.kmp.data.CharactersResponse
import com.santimattius.kmp.data.NetworkCharacter
import com.santimattius.kmp.data.sources.CharacterNetworkDataSource

class FakeCharacterNetworkDataSource : CharacterNetworkDataSource {
    private val jsonLoader = JsonLoader
    override suspend fun find(id: Long): Result<NetworkCharacter> {
        return all().fold(onSuccess = { characters ->
            runCatching { characters.first { it.id == id } }
        }, onFailure = { Result.failure(it) })
    }

    override suspend fun all(): Result<List<NetworkCharacter>> {
        return runCatching {
            jsonLoader.load<CharactersResponse>("characters.json").results
        }
    }
}