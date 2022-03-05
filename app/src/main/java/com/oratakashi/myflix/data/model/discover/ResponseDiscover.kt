package com.oratakashi.myflix.data.model.discover

import com.google.gson.annotations.SerializedName
import com.oratakashi.myflix.data.model.discover.DataDiscover

data class ResponseDiscover(
    @SerializedName("results") val data : List<DataDiscover>?,
    @SerializedName("page") val page : Int?,
    @SerializedName("total_pages") val total_pages : Int?
)
