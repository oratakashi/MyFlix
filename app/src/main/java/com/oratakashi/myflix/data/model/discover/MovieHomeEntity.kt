package com.oratakashi.myflix.data.model.discover

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moviehome")
data class MovieHomeEntity(
    val backdrop_path: String?,
    @PrimaryKey val id: Int,
    val overview: String?,
    val poster_path: String?,
    val title: String?,
    val release_date: String?,
    val id_genre: String?
) {
    fun toDiscover(): DataDiscover {
        return DataDiscover(
            backdrop_path, id, overview, poster_path, title, release_date
        )
    }
}
