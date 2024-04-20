package com.santimattius.kmp

import com.santimattius.kmp.data.db.DriverFactory
import com.santimattius.kmp.viewmodels.CharactersViewModel
import org.koin.dsl.module

val platformModule = module {
    single { DriverFactory() }
    factory { CharactersViewModel(get(), get()) }
}