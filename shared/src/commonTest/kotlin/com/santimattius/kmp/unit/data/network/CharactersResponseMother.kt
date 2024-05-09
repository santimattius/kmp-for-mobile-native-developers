package com.santimattius.kmp.unit.data.network

import com.santimattius.kmp.JsonLoader
import com.santimattius.kmp.data.CharactersResponse
import com.santimattius.kmp.data.NetworkCharacter

object CharactersResponseMother {
    fun characters(): List<NetworkCharacter> {
        return JsonLoader.load<CharactersResponse>("characters.json").results
    }
}
