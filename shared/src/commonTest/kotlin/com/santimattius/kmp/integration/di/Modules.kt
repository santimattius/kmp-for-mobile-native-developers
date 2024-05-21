package com.santimattius.kmp.integration.di

import androidx.room.RoomDatabase
import app.cash.sqldelight.db.SqlDriver
import com.santimattius.kmp.core.db.AppDatabase
import com.santimattius.kmp.integration.data.db.getInMemoryDataBase
import com.santimattius.kmp.integration.data.db.testDbDriver
import com.santimattius.kmp.integration.data.network.MockClient
import com.santimattius.kmp.integration.data.network.ResponseInterceptor
import com.santimattius.kmp.integration.data.network.testKtorClient
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

val testPlatformModule: Module = module {
    single<SqlDriver> { testDbDriver() }
    single<MockClient> { MockClient() }
    single<HttpClient> { testKtorClient(get()) }
    single<RoomDatabase.Builder<AppDatabase>> { getInMemoryDataBase() }
}