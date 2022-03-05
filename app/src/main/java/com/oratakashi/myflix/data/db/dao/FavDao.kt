package com.oratakashi.myflix.data.db.dao

import androidx.room.*
import com.oratakashi.myflix.data.model.fav.DataFav
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(data: DataFav)

    @Query("Select * from favorite where id=:id")
    suspend fun getById(id: String) : List<DataFav>

    @Query("Select * from favorite where id=:id")
    fun observeById(id: String) : Flow<List<DataFav>>

    @Delete
    suspend fun delete(data: DataFav)

    @Query("Select * from favorite order by created_at desc")
    fun getAll() : Flow<List<DataFav>>
}