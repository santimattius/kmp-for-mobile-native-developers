package com.santimattius.kmp.data

import com.santimattius.kmp.data.db.CharacterEntity
import com.santimattius.kmp.domain.Character


fun NetworkCharacter.asDomainModel(): Character {
    return Character(id, name, image)
}

fun List<CharacterEntity>.asDomainsModels() = this.map { it.asDomainModel() }
fun CharacterEntity.asDomainModel(): Character {
    return Character(id, name, image)
}