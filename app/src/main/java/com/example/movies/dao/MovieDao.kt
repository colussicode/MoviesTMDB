package com.example.movies.dao

import androidx.room.*
import com.example.movies.data.FavouriteMovieEntity
import com.example.movies.data.MovieSearchEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearch(movieQuerySearch: MovieSearchEntity)

    @Query("SELECT * FROM movie_searches WHERE typed_string LIKE '%' || :search || '%'")
    fun getSearch(search: String): List<MovieSearchEntity>

    @Insert()
    fun insertMovieId(id: FavouriteMovieEntity)

    @Query("SELECT * FROM favourite_movies")
    fun getFavouriteMovies(): List<FavouriteMovieEntity>

    @Delete()
    fun deleteMovieId(favouriteMovieId: FavouriteMovieEntity)
}