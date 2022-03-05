package com.oratakashi.myflix.data

import com.oratakashi.myflix.data.model.discover.DataDiscover
import com.oratakashi.myflix.data.model.fav.DataFav
import com.oratakashi.myflix.data.model.genre.GenreEntity
import com.oratakashi.myflix.data.model.genre.ResponseGenre
import com.oratakashi.myflix.data.model.movie.ResponseMovie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getGenre(): Flow<List<GenreEntity>>

    fun getDiscover(): Flow<List<DataDiscover>>

    fun getDiscover(genre: Int): Flow<List<DataDiscover>>

    fun getDetailMovie(id: Int): Flow<ResponseMovie>

    fun addFav(data: ResponseMovie): Flow<List<DataFav>>

    fun isFav(data: ResponseMovie): Flow<List<DataFav>>

    fun getAllFav(): Flow<List<DataFav>>
}