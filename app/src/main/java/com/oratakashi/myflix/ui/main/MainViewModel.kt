package com.oratakashi.myflix.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oratakashi.myflix.data.MovieRepository
import com.oratakashi.myflix.data.model.discover.DataDiscover
import com.oratakashi.myflix.data.model.genre.GenreEntity
import com.oratakashi.myflix.data.model.genre.ResponseGenre
import com.oratakashi.viewbinding.core.binding.livedata.liveData
import com.oratakashi.viewbinding.core.tools.State
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val repo: MovieRepository
) : ViewModel() {
    private val job: Job by lazy { Job() }

    private val _genre: MutableLiveData<State<List<GenreEntity>>> by liveData(State.default())
    val genre: LiveData<State<List<GenreEntity>>> = _genre

    private val _discover: MutableLiveData<State<List<DataDiscover>>> by liveData()
    val discover: LiveData<State<List<DataDiscover>>> = _discover

    private val _movie: MutableLiveData<State<Pair<Int, List<DataDiscover>>>> by liveData()
    val movie: LiveData<State<Pair<Int, List<DataDiscover>>>> = _movie

    fun getHome() {
        viewModelScope.launch {
            repo.getDiscover()
                .onStart { _discover.postValue(State.loading()) }
//                .catch { _discover.postValue(State.fail(it, it.message)) }
                .zip(repo.getGenre())
                { discover, genre ->
                    _discover.postValue(State.success(discover))
                    _genre.postValue(State.success(genre))
                }.collect()
        }
    }

    fun getGenre(id: Int) {
        viewModelScope.launch {
            repo.getDiscover(id)
                .catch { _movie.postValue(State.fail(it, it.message)) }
                .collect { _movie.postValue(State.success(id to it)) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}