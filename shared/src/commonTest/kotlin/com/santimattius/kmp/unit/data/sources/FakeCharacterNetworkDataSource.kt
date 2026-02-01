package com.santimattius.kmp.unit.data.sources

import com.santimattius.kmp.JsonLoader
import com.santimattius.kmp.data.CharactersResponse
import com.santimattius.kmp.data.NetworkCharacter
import com.santimattius.kmp.data.sources.CharacterNetworkDataSource

/**
 * Fake network data source for ViewModel/use-case tests.
 *
 * **Default success:** Always loads [characters.json] and returns success. Use for happy-path tests.
 * For empty-list or failure scenarios, use [StubCharacterNetworkDataSource] or Mokkery mocks.
 *
 * **Optional override:** Call [setResponse] to configure a custom result for the next [all] call
 * (e.g. empty list or [Result.failure]). After one [all] call, the override is cleared and
 * the default (characters.json) is used again.
 */
class FakeCharacterNetworkDataSource : CharacterNetworkDataSource {

    private val jsonLoader = JsonLoader
    private var override: Result<List<NetworkCharacter>>? = null

    /**
     * Override the result for the next [all] call. Use for empty-list or failure tests.
     * Cleared after one [all] invocation.
     */
    fun setResponse(result: Result<List<NetworkCharacter>>) {
        override = result
    }

    override suspend fun find(id: Long): Result<NetworkCharacter> {
        return all().fold(onSuccess = { characters ->
            runCatching { characters.first { it.id == id } }
        }, onFailure = { Result.failure(it) })
    }

    override suspend fun all(): Result<List<NetworkCharacter>> {
        val over = override
        if (over != null) {
            override = null
            return over
        }
        return runCatching {
            jsonLoader.load<CharactersResponse>("characters.json").results
        }
    }
}