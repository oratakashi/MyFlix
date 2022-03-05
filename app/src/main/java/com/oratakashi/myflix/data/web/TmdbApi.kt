package com.oratakashi.myflix.data.web

import com.oratakashi.myflix.data.model.discover.ResponseDiscover
import com.oratakashi.myflix.data.model.genre.ResponseGenre
import com.oratakashi.myflix.data.model.movie.ResponseMovie

class TmdbApi(
    private val api: TmdbApiClient
): TmdbApiClient {
    override suspend fun getGenre(): ResponseGenre {
        return api.getGenre()
    }

    override suspend fun getDiscover(
        include_adult: Boolean,
        page: Int,
        sortBy: String
    ): ResponseDiscover {
        return api.getDiscover()
    }

    override suspend fun getDiscover(genre: Int, page: Int, sortBy: String): ResponseDiscover {
        return api.getDiscover(genre)
    }

    override suspend fun getDetailMovie(id: Int): ResponseMovie {
        return api.getDetailMovie(id)
    }
}