package com.oratakashi.myflix.data.web

import com.oratakashi.myflix.data.model.discover.ResponseDiscover
import com.oratakashi.myflix.data.model.genre.ResponseGenre
import com.oratakashi.myflix.data.model.movie.ResponseMovie
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiClient {
    @GET("genre/movie/list")
    suspend fun getGenre(): ResponseGenre

    @GET("discover/movie")
    suspend fun getDiscover(
        @Query("include_adult") include_adult: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc"
    ): ResponseDiscover

    @GET("discover/movie")
    suspend fun getDiscover(
        @Query("with_genres") genre: Int,
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc"
    ): ResponseDiscover

    @GET("movie/{id}")
    suspend fun getDetailMovie(
        @Path("id") id: Int
    ): ResponseMovie
}