package com.oratakashi.myflix.data.model.genre

import com.google.gson.annotations.SerializedName
import com.oratakashi.myflix.data.model.genre.DataGenre

data class ResponseGenre(
    @field:SerializedName("genres") val genres : List<DataGenre>,
)
