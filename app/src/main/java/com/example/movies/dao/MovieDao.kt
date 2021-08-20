package com.example.movies.dao

import androidx.room.*
import com.example.movies.data.FavouriteMovieEntity
import com.example.movies.data.MovieSearchEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearch(movieQuerySearch: MovieSearchEntity)

    @Insert()
    fun insertMovieId(id: FavouriteMovieEntity)

    @Query("SELECT * FROM favourite_movies")
    fun searchMovies(): List<FavouriteMovieEntity>

    @Delete(entity = FavouriteMovieEntity::class)
    fun deleteMovieId(favouriteMovieId: FavouriteMovieEntity)
}