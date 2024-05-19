package com.santimattius.kmp

import com.santimattius.kmp.viewmodels.CharactersViewModel
import com.santimattius.kmp.viewmodels.FavoritesViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object Injector : KoinComponent {

    private val charactersViewModel: CharactersViewModel by inject()
    private val favoritesViewModel: FavoritesViewModel by inject()

    fun provideCharacterViewModel(): CharactersViewModel = charactersViewModel
    fun provideFavoritesViewModel(): FavoritesViewModel = favoritesViewModel
}