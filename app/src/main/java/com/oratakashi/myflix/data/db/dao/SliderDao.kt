package com.oratakashi.myflix.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oratakashi.myflix.data.model.discover.SliderEntity

@Dao
interface SliderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(data: List<SliderEntity>)

    @Query("Select * from slider")
    suspend fun getAll(): List<SliderEntity>

    @Query("Delete from slider")
    suspend fun deleteAll()
}