package com.oratakashi.myflix.data.model.fav

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oratakashi.myflix.data.model.discover.DataDiscover
import java.util.*

@Entity(tableName = "favorite")
data class DataFav(
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val backdrop_path : String?,
    val overview : String?,
    val poster_path : String?,
    val title : String?,
    val release_date : String?,
    val vote_average: Float?,
    val created_at : String? = Calendar.getInstance().timeInMillis.toString()
) {
    fun toDiscover(): DataDiscover {
        return DataDiscover(
            backdrop_path,
            runCatching { id.toInt() }.getOrDefault(0),
            overview, poster_path, title, release_date
        )
    }
}