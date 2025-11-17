package com.santimattius.kmp.data

import com.santimattius.kmp.CharacterEntity
import com.santimattius.kmp.domain.Character


fun NetworkCharacter.asDomainModel(): Character {
    return Character(id, name, image, false)
}

fun List<CharacterEntity>.asDomainsModels() = this.map { it.asDomainModel() }

fun CharacterEntity.asDomainModel(): Character {
    return Character(id, name, image, favorite ?: false)
}