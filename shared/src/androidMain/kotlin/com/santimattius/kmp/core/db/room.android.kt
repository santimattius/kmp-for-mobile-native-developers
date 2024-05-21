package com.santimattius.kmp.core.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

actual class RoomBuilder(
    private val appContext: Context
) {
    actual fun builder(): RoomDatabase.Builder<AppDatabase> {
        val appContext = appContext.applicationContext
        val dbFile = appContext.getDatabasePath(DATABASE_NAME)

        return Room.databaseBuilder<AppDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        ).setDriver(BundledSQLiteDriver())
    }
}