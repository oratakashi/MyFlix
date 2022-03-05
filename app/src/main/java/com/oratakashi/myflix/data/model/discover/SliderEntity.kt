package com.oratakashi.myflix.data.model.discover

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "slider")
data class SliderEntity(
    val backdrop_path: String?,
    @PrimaryKey val id: Int,
    val overview: String?,
    val poster_path: String?,
    val title: String?,
    val release_date: String?
) {
    fun toDiscover(): DataDiscover {
        return DataDiscover(
            backdrop_path, id, overview, poster_path, title, release_date
        )
    }
}
