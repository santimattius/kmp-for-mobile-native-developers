package com.santimattius.kmp.integration.data.db

import androidx.room.Room
import androidx.room.RoomDatabase
import com.santimattius.kmp.core.db.AppDatabase

actual fun getInMemoryDataBase(): RoomDatabase.Builder<AppDatabase> {
    return Room.inMemoryDatabaseBuilder { AppDatabase::class.instantiateImpl() }
}