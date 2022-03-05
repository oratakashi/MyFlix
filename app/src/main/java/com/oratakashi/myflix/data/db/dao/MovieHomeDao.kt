package com.oratakashi.myflix.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oratakashi.myflix.data.model.discover.MovieHomeEntity

@Dao
interface MovieHomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(data: List<MovieHomeEntity>)

    @Query("Select * from moviehome where id_genre=:id")
    suspend fun getAll(id: String): List<MovieHomeEntity>

    @Query("Delete from moviehome where id_genre=:id")
    suspend fun deleteAll(id: String)
}