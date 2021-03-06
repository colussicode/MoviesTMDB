package com.example.movies.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movies.dao.MovieDao

const val DATABASE_NAME = "movies"

@Database(
    entities = [MovieSearchEntity::class, FavouriteMovieEntity::class],
    version = 5,
    exportSchema = false
)
abstract class RoomSearchDataBase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        @Volatile
        private var instance: RoomSearchDataBase? = null

        fun getInstance(context: Context): RoomSearchDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): RoomSearchDataBase {
            return Room.databaseBuilder(context, RoomSearchDataBase::class.java, DATABASE_NAME).fallbackToDestructiveMigration()
                .build()
        }
    }
}