package com.santimattius.kmp.viewmodels

import com.santimattius.kmp.domain.GetAllCharacters
import com.santimattius.kmp.domain.RefreshCharacters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CharactersViewModel(
    getAllCharacters: GetAllCharacters,
    private val refreshCharacters: RefreshCharacters,
    private val viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
) {


    var characters = getAllCharacters().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1000L),
        initialValue = emptyList()
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