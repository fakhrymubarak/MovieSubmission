package com.fakhry.movie.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fakhry.movie.data.source.local.entity.FavMovieEntity
import com.fakhry.movie.data.source.local.entity.FavTvShowEntity


@Database(entities = [FavMovieEntity::class, FavTvShowEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun dao(): MovieDao

    companion object {

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            if (INSTANCE == null) {
                synchronized(MovieDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                            MovieDatabase::class.java, "MovieDatabase.db")
                            .build()
                    }
                }
            }
            return INSTANCE as MovieDatabase
        }
    }
}