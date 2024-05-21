package com.santimattius.kmp.integration.data.db

import androidx.room.RoomDatabase
import com.santimattius.kmp.core.db.AppDatabase

expect fun getInMemoryDataBase(): RoomDatabase.Builder<AppDatabase>