package com.oratakashi.myflix.ui.fav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oratakashi.myflix.data.MovieRepository
import com.oratakashi.myflix.data.model.fav.DataFav
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repo: MovieRepository
) : ViewModel() {
    private val job: Job by lazy { Job() }

    private val _fav: MutableStateFlow<List<DataFav>> by lazy { MutableStateFlow(emptyList()) }
    val fav: StateFlow<List<DataFav>> = _fav

    init {
        viewModelScope.launch {
            repo.getAllFav().collect(_fav::emit)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}