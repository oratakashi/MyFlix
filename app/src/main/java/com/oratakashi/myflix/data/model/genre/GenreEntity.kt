package com.oratakashi.myflix.data.model.genre

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class GenreEntity(
    @PrimaryKey val id: Int,
    val name: String
) {
    fun toGenre(): DataGenre {
        return DataGenre(id, name)
    }
}
