package com.santimattius.kmp.core.db

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

const val DATABASE_NAME = "app_database"

expect class RoomBuilder {
    fun builder(): RoomDatabase.Builder<AppDatabase>
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): AppDatabase {
    return builder
        .setQueryCoroutineContext(dispatcher)
        .setDriver(BundledSQLiteDriver())
        .build()
}
