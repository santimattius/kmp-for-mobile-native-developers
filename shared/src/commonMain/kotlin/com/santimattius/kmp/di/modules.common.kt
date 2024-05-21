package com.santimattius.kmp.di

import androidx.room.RoomDatabase
import app.cash.sqldelight.db.SqlDriver
import com.santimattius.kmp.CharactersDatabase
import com.santimattius.kmp.core.CharacterRepository
import com.santimattius.kmp.core.db.AppDatabase
import com.santimattius.kmp.core.db.RoomBuilder
import com.santimattius.kmp.core.db.createDatabase
import com.santimattius.kmp.core.db.getRoomDatabase
import com.santimattius.kmp.core.network.apiClient
import com.santimattius.kmp.core.sources.CharacterLocalDataSource
import com.santimattius.kmp.core.sources.CharacterNetworkDataSource
import com.santimattius.kmp.core.sources.RoomCharacterLocalDataSource
import com.santimattius.kmp.core.sources.ktor.KtorCharacterNetworkDataSource
import com.santimattius.kmp.core.sources.sqldelight.SQLDelightCharacterLocalDataSource
import com.santimattius.kmp.domain.AddToFavorite
import com.santimattius.kmp.domain.FindCharacterById
import com.santimattius.kmp.domain.GetAllCharacters
import com.santimattius.kmp.domain.RefreshCharacters
import com.santimattius.kmp.domain.RemoveFromFavorites
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

enum class DataBases {
    ROOM, SQLDELIGHT;
}

val coreModule = module {
    single<HttpClient> { apiClient("https://rickandmortyapi.com") }
}

val sharedModule = module {
    single<CharacterNetworkDataSource> { KtorCharacterNetworkDataSource(get<HttpClient>()) }

    single<CharactersDatabase> { createDatabase(get<SqlDriver>()) }
    single<AppDatabase> { getRoomDatabase(builder = get<RoomDatabase.Builder<AppDatabase>>()) }

    single<CharacterLocalDataSource>(named(DataBases.SQLDELIGHT)) {
        SQLDelightCharacterLocalDataSource(
            db = get<CharactersDatabase>()
        )
    }
    single<CharacterLocalDataSource>(named(DataBases.ROOM)) {
        RoomCharacterLocalDataSource(
            characterDao = get<AppDatabase>().getCharacterDao()
        )
    }
    single {
        CharacterRepository(
            local = get<CharacterLocalDataSource>(named(DataBases.ROOM)),
            network = get<CharacterNetworkDataSource>()
        )
    }

    factory<GetAllCharacters> { GetAllCharacters(get<CharacterRepository>()) }
    factory<FindCharacterById> { FindCharacterById(get<CharacterRepository>()) }
    factory<RefreshCharacters> { RefreshCharacters(get<CharacterRepository>()) }

    factory { AddToFavorite(get<CharacterRepository>()) }
    factory { RemoveFromFavorites(get<CharacterRepository>()) }
}

expect val platformModule: Module