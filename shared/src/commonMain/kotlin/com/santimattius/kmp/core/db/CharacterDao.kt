package com.santimattius.kmp.core.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    fun getAll(): Flow<List<LocalCharacter>>

    @Query("SELECT * FROM characters WHERE favorite = true")
    fun getFavorite(): Flow<List<LocalCharacter>>

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun findById(id: Long): LocalCharacter

    @Query("UPDATE characters SET favorite = :favorite WHERE id = :id")
    suspend fun updateFavorite(id: Long, favorite: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: LocalCharacter)

    @Query("DELETE FROM characters")
    suspend fun deleteAll()

}