package com.santimattius.kmp.core

import com.santimattius.kmp.CharacterEntity
import com.santimattius.kmp.core.db.LocalCharacter
import com.santimattius.kmp.domain.Character


fun NetworkCharacter.asDomainModel(): Character {
    return Character(id, name, image, false)
}

fun List<CharacterEntity>.asDomainsModels() = this.map { it.asDomainModel() }
fun CharacterEntity.asDomainModel(): Character {
    return Character(id, name, image, favorite ?: false)
}

fun List<LocalCharacter>.toCharacters() = map {
    it.toCharacter()
}

fun LocalCharacter.toCharacter(): Character {
    return Character(id, name, image, favorite)
}
fun Character.toLocalCharacter(): LocalCharacter {
    return LocalCharacter(id, name, image, isFavorite)
}