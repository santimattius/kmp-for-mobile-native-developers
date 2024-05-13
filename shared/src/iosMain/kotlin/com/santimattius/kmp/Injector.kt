package com.santimattius.kmp

import com.santimattius.kmp.viewmodels.CharactersViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object Injector : KoinComponent {

    private val charactersViewModel: CharactersViewModel by inject()

    fun provideCharacterViewModel(): CharactersViewModel = charactersViewModel
}