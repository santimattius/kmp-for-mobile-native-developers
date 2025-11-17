package com.santimattius.kmp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.kmp.domain.AddToFavorite
import com.santimattius.kmp.domain.Character
import com.santimattius.kmp.domain.GetAllCharacters
import com.santimattius.kmp.domain.RefreshCharacters
import com.santimattius.kmp.domain.RemoveFromFavorites
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CharactersViewModel(
    getAllCharacters: GetAllCharacters,
    private val refreshCharacters: RefreshCharacters,
    private val addToFavorite: AddToFavorite,
    private val removeFromFavorite: RemoveFromFavorites,
    viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
):ViewModel(viewModelScope) {


    var characters = getAllCharacters().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1000L),
        initialValue = persistentListOf()
    )

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            refreshCharacters.invoke()
        }
    }

    fun addToFavorites(character: Character) {
        viewModelScope.launch {
            if (character.isFavorite) {
                removeFromFavorite(character.id)
            } else {
                addToFavorite(character.id)
            }
        }
    }
}