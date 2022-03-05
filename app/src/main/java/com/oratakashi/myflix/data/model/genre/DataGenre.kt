package com.oratakashi.myflix.data.model.genre

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataGenre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
) : Parcelable {
    fun toGenreEntity() : GenreEntity{
        return GenreEntity(id, name)
    }
}
