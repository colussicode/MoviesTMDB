package com.example.movies.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.movies.data.MovieEntity

@Dao
interface MovieDao {

    @Insert()
    fun insertSearch(vararg movieQuerySearch: MovieEntity)
}