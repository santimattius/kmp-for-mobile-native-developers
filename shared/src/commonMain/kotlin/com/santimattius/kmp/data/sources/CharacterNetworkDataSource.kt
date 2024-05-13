package com.santimattius.kmp.data.sources

import com.santimattius.kmp.data.NetworkCharacter
import com.santimattius.kmp.domain.Character

interface CharacterNetworkDataSource {
    suspend fun find(id: Long): Result<NetworkCharacter>
    suspend fun all(): Result<List<NetworkCharacter>>

}
