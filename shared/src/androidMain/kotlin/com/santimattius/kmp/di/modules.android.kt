package com.santimattius.kmp.di

import app.cash.sqldelight.db.SqlDriver
import com.santimattius.kmp.AndroidCharactersViewModel
import com.santimattius.kmp.data.db.DriverFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        //single<SqlDriver> { DriverFactory(get()).createDriver() }
        viewModel { AndroidCharactersViewModel(get(), get()) }
    }