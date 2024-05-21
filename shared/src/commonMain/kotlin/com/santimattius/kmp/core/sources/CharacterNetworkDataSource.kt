package com.santimattius.kmp.core.sources

import com.santimattius.kmp.core.NetworkCharacter

interface CharacterNetworkDataSource {
    suspend fun find(id: Long): Result<NetworkCharacter>
    suspend fun all(): Result<List<NetworkCharacter>>

}
