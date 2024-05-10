package com.santimattius.kmp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.sqldelight.db.SqlDriver
import com.santimattius.kmp.data.db.DriverFactory
import com.santimattius.kmp.domain.GetAllCharacters
import com.santimattius.kmp.domain.RefreshCharacters
import com.santimattius.kmp.viewmodels.CharactersViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val platformModule = module {
    single<SqlDriver> { DriverFactory(androidContext()).createDriver() }
    viewModel { AndroidCharactersViewModel(get(), get()) }
}

class AndroidCharactersViewModel(
    getAllCharacters: GetAllCharacters,
    refreshCharacters: RefreshCharacters,
) : ViewModel() {

    private val delegate = CharactersViewModel(
        getAllCharacters = getAllCharacters,
        refreshCharacters = refreshCharacters,
        viewModelScope = viewModelScope
    )

    var characters = delegate.characters
}