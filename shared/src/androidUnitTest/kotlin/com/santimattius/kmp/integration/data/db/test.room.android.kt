package com.santimattius.kmp.integration.data.db

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import com.santimattius.kmp.core.db.AppDatabase

actual fun getInMemoryDataBase(): RoomDatabase.Builder<AppDatabase> {
    return Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        AppDatabase::class.java
    )
}