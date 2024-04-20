package com.santimattius.kmp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.kmp.domain.GetAllCharacters
import com.santimattius.kmp.domain.RefreshCharacters
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CharactersViewModel(
    getAllCharacters: GetAllCharacters,
    private val refreshCharacters: RefreshCharacters,
) : ViewModel() {


    var characters = getAllCharacters().stateIn(
        viewModelScope, SharingStarted.Eagerly, emptyList()
    )

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            refreshCharacters.invoke()
        }
    }
}