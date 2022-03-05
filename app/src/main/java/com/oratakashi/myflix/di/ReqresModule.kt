package com.oratakashi.myflix.di

import com.oratakashi.myflix.data.MovieDataStore
import com.oratakashi.myflix.data.MovieRepository
import com.oratakashi.myflix.ui.detail.DetailViewModel
import com.oratakashi.myflix.ui.fav.FavoriteViewModel
import com.oratakashi.myflix.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val reqresModule = module {
    single<MovieRepository> { MovieDataStore(
        get(),
        get(),
        get(),
        get(),
        get()
    ) }

    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}