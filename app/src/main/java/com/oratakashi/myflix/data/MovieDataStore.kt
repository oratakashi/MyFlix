package com.oratakashi.myflix.data

import com.oratakashi.myflix.data.db.dao.FavDao
import com.oratakashi.myflix.data.db.dao.GenreDao
import com.oratakashi.myflix.data.db.dao.MovieHomeDao
import com.oratakashi.myflix.data.db.dao.SliderDao
import com.oratakashi.myflix.data.model.discover.DataDiscover
import com.oratakashi.myflix.data.model.fav.DataFav
import com.oratakashi.myflix.data.model.genre.GenreEntity
import com.oratakashi.myflix.data.model.genre.ResponseGenre
import com.oratakashi.myflix.data.model.movie.ResponseMovie
import com.oratakashi.myflix.data.web.TmdbApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class MovieDataStore(
    private val webService: TmdbApi,
    private val db: FavDao,
    private val genre: GenreDao,
    private val slider: SliderDao,
    private val movieHome: MovieHomeDao
) : MovieRepository {
    @OptIn(FlowPreview::class)
    override fun getGenre(): Flow<List<GenreEntity>> {
        return flow {
            val genre = webService.getGenre()
            emit(genre.genres.map { data -> data.toGenreEntity() })
        }
            .onEach {
                genre.add(it)
            }
//            .flatMapMerge { genre.getAll() }
            .catch { emit(genre.getAll()) }
            .flowOn(Dispatchers.IO)
    }

    @OptIn(FlowPreview::class)
    override fun getDiscover(): Flow<List<DataDiscover>> {
        return flow {
            val discover = webService.getDiscover()
            discover.data?.let { emit(it.subList(0, 5)) }
        }
            .onEach {
                slider.add(it.map { data -> data.toSlider() })
            }
//            .flatMapMerge { slider.getAll().map { it.map { data -> data.toDiscover() } } }
            .catch { emit(slider.getAll().map { it.toDiscover() }) }
            .flowOn(Dispatchers.IO)
    }

    @OptIn(FlowPreview::class)
    override fun getDiscover(genre: Int): Flow<List<DataDiscover>> {
        return flow {
            val discover = webService.getDiscover(genre)
            discover.data?.let { emit(it) }
        }
            .onEach {
                movieHome.add(it.map { data -> data.toMovieHome(genre.toString()) })
                movieHome.getAll(genre.toString())
            }
            .catch { emit(movieHome.getAll(genre.toString()).map { it.toDiscover() }) }
//            .flatMapMerge { movieHome.getAll().map { it.map { data -> data.toDiscover() } } }
            .flowOn(Dispatchers.IO)
    }

    override fun getDetailMovie(id: Int): Flow<ResponseMovie> {
        return flow {
            val movie = webService.getDetailMovie(id)
            emit(movie)
        }.flowOn(Dispatchers.IO)
    }

    override fun addFav(data: ResponseMovie): Flow<List<DataFav>> {
        return flow {
            if(db.getById(data.id.orEmpty()).isEmpty()) {
                db.add(data.toFav())
            } else {
                db.delete(data.toFav())
            }
            val currentFav = db.getById(data.id.orEmpty())
            emit(currentFav)
        }
    }

    override fun isFav(data: ResponseMovie): Flow<List<DataFav>> {
        return db.observeById(data.id.orEmpty())
    }

    override fun getAllFav(): Flow<List<DataFav>> {
        return db.getAll()
    }
}