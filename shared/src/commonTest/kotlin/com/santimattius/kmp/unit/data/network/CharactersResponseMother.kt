package com.santimattius.kmp.unit.data.network

import com.santimattius.kmp.JsonLoader
import com.santimattius.kmp.core.CharactersResponse
import com.santimattius.kmp.core.NetworkCharacter

object CharactersResponseMother {
    fun characters(): List<NetworkCharacter> {
        return JsonLoader.load<CharactersResponse>("characters.json").results
    }
}
