package com.oratakashi.myflix.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oratakashi.myflix.data.MovieRepository
import com.oratakashi.myflix.data.model.movie.ResponseMovie
import com.oratakashi.viewbinding.core.binding.livedata.liveData
import com.oratakashi.viewbinding.core.tools.State
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repo: MovieRepository
) : ViewModel() {
    private val job: Job by lazy { Job() }

    private val _movie: MutableLiveData<State<ResponseMovie>> by liveData(State.default())
    val movie: LiveData<State<ResponseMovie>> = _movie

    private val _isFav: MutableStateFlow<Boolean> by lazy {
        MutableStateFlow(false)
    }

    val isFav: StateFlow<Boolean> = _isFav

    fun getDetailMovie(id: Int) {
        viewModelScope.launch {
            repo.getDetailMovie(id)
                .onStart { _movie.postValue(State.loading()) }
                .catch { _movie.postValue(State.fail(it, it.message)) }
                .collect {
                    _movie.postValue(State.success(it))
                }
        }
    }

    fun isFav(data: ResponseMovie) {
        viewModelScope.launch {
            repo.isFav(data).collect {
                if(it.isNotEmpty()) {
                    _isFav.emit(true)
                } else {
                    _isFav.emit(false)
                }
            }
        }
    }

    fun addFav(data: ResponseMovie) {
        viewModelScope.launch {
            repo.addFav(data).collectLatest {
                if(it.isNotEmpty()) {
                    _isFav.emit(true)
                } else {
                    _isFav.emit(false)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}