package com.santimattius.kmp.di

import app.cash.sqldelight.db.SqlDriver
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
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

val coreModule = module {
    single<HttpClient> { apiClient("https://rickandmortyapi.com") }
}

val sharedModule = module {
    single<CharacterNetworkDataSource> { KtorCharacterNetworkDataSource(get<HttpClient>()) }
    single<CharactersDatabase> { createDatabase(get<SqlDriver>()) }
    single<CharacterLocalDataSource> { SQLDelightCharacterLocalDataSource(db = get<CharactersDatabase>()) }
    single { CharacterRepository(local = get<CharacterLocalDataSource>(), network = get<CharacterNetworkDataSource>()) }

    factory<GetAllCharacters> { GetAllCharacters(get<CharacterRepository>()) }
    factory<FindCharacterById> { FindCharacterById(get<CharacterRepository>()) }
    factory<RefreshCharacters> { RefreshCharacters(get<CharacterRepository>()) }

    factory { AddToFavorite(get<CharacterRepository>()) }
    factory { RemoveFromFavorites(get<CharacterRepository>()) }
}

expect val platformModule: Module