package com.oratakashi.myflix.data.model.movie

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.oratakashi.myflix.data.model.fav.DataFav
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseMovie(
    @SerializedName("id") val id : String?,
    @SerializedName("backdrop_path") val backdrop_path : String?,
    @SerializedName("poster_path") val poster_path : String?,
    @SerializedName("title") val title : String?,
    @SerializedName("overview") val overview : String?,
    @SerializedName("release_date") val release_date : String?,
    @SerializedName("vote_average") val vote_average : Float?,
) : Parcelable {
    fun toFav() : DataFav {
        return DataFav(
            id.orEmpty(),
            backdrop_path, overview, poster_path, title, release_date, vote_average
        )
    }
}
