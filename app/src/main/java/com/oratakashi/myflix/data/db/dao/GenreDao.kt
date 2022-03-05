package com.oratakashi.myflix.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oratakashi.myflix.data.model.genre.GenreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(data: List<GenreEntity>)

    @Query("Select * from genre")
    suspend fun getAll(): List<GenreEntity>

    @Query("Delete from genre")
    suspend fun deleteAll()
}