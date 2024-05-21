package com.santimattius.kmp.di

import androidx.room.RoomDatabase
import app.cash.sqldelight.db.SqlDriver
import com.santimattius.kmp.core.db.AppDatabase
import com.santimattius.kmp.core.db.DriverFactory
import com.santimattius.kmp.core.db.RoomBuilder
import com.santimattius.kmp.viewmodels.CharactersViewModel
import com.santimattius.kmp.viewmodels.FavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<SqlDriver> { DriverFactory(get()).createDriver() }
        single<RoomDatabase.Builder<AppDatabase>> { RoomBuilder(get()).builder() }
        viewModel { CharactersViewModel(get(), get(), get(), get()) }
        viewModel { FavoritesViewModel(get()) }
    }