package com.santimattius.kmp

import com.santimattius.kmp.data.db.DriverFactory
import com.santimattius.kmp.viewmodels.CharactersViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val platformModule = module {
    single { DriverFactory(androidContext()) }
    viewModel { CharactersViewModel(get(), get()) }
}