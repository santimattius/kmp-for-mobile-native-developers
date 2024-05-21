package com.santimattius.kmp.core.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalCharacter::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCharacterDao(): CharacterDao
}