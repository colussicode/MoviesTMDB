package com.example.movies.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.dao.MovieDao

@Database(entities = arrayOf(MovieEntity::class), version = 1)
abstract class Database : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}