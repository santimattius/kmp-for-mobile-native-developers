package com.santimattius.kmp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.kmp.data.CharacterRepository
import com.santimattius.kmp.domain.Character
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val characterRepository: CharacterRepository,
    viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
) : ViewModel(viewModelScope) {

    var characters = characterRepository.allFavoritesCharacters.map {
        it.toImmutableList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1000L),
        initialValue = persistentListOf()
    )

    fun addToFavorites(character: Character) {
        viewModelScope.launch {
            if (character.isFavorite) {
                characterRepository.removeFromFavorite(character.id)
            } else {
                characterRepository.addToFavorite(character.id)
            }
        }
    }
}