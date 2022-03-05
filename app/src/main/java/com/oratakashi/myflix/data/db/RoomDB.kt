package com.oratakashi.myflix.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oratakashi.myflix.data.db.dao.FavDao
import com.oratakashi.myflix.data.db.dao.GenreDao
import com.oratakashi.myflix.data.db.dao.MovieHomeDao
import com.oratakashi.myflix.data.db.dao.SliderDao
import com.oratakashi.myflix.data.model.discover.MovieHomeEntity
import com.oratakashi.myflix.data.model.discover.SliderEntity
import com.oratakashi.myflix.data.model.fav.DataFav
import com.oratakashi.myflix.data.model.genre.GenreEntity

@Database(
    entities = [
        DataFav::class,
        GenreEntity::class,
        SliderEntity::class,
        MovieHomeEntity::class
    ],
    version = 1
)
abstract class RoomDB : RoomDatabase() {
    abstract fun fav() : FavDao
    abstract fun genre(): GenreDao
    abstract fun slider(): SliderDao
    abstract fun movieHome(): MovieHomeDao

    companion object {
        @Volatile
        private var instance: RoomDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RoomDB::class.java,
            "MyFlix.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}