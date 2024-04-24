package com.santimattius.kmp.di

import com.santimattius.kmp.CharactersDatabase
import com.santimattius.kmp.data.CharacterRepository
import com.santimattius.kmp.data.db.createDatabase
import com.santimattius.kmp.data.network.apiClient
import com.santimattius.kmp.data.sources.CharacterLocalDataSource
import com.santimattius.kmp.data.sources.CharacterNetworkDataSource
import com.santimattius.kmp.data.sources.ktor.KtorCharacterNetworkDataSource
import com.santimattius.kmp.data.sources.sqldelight.SQLDelightCharacterLocalDataSource
import com.santimattius.kmp.domain.AddToFavorite
import com.santimattius.kmp.domain.FindCharacterById
import com.santimattius.kmp.domain.GetAllCharacters
import com.santimattius.kmp.domain.RefreshCharacters
import com.santimattius.kmp.domain.RemoveFromFavorites
import org.koin.dsl.module

val sharedModule = module {

    single { apiClient("https://rickandmortyapi.com") }
    single<CharacterNetworkDataSource> { KtorCharacterNetworkDataSource(get()) }
    single<CharactersDatabase> { createDatabase(get()) }
    single<CharacterLocalDataSource> { SQLDelightCharacterLocalDataSource(db = get()) }
    single { CharacterRepository(local = get(), network = get()) }

    factory { GetAllCharacters(get()) }
    factory { FindCharacterById(get()) }
    factory { RefreshCharacters(get()) }

    factory { AddToFavorite(get()) }
    factory { RemoveFromFavorites(get()) }
}