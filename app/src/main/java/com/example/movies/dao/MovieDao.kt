package com.example.movies.dao

import androidx.room.*
import com.example.movies.data.FavouriteMovieEntity
import com.example.movies.data.MovieSearchEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertSearch(movieQuerySearch: MovieSearchEntity)

    @Insert()
    fun insertMovieId(favouriteMovieId: FavouriteMovieEntity)

    @Delete(entity = FavouriteMovieEntity::class)
    fun deleteMovieId(favouriteMovieId: FavouriteMovieEntity)
}