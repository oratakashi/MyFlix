package com.oratakashi.myflix.di

import com.oratakashi.myflix.data.db.RoomDB
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { RoomDB(androidContext()) }
    single { get<RoomDB>().fav() }
    single { get<RoomDB>().genre() }
    single { get<RoomDB>().slider() }
    single { get<RoomDB>().movieHome() }
}