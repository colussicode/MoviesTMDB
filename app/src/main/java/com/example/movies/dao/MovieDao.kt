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
    fun getFavouriteMovies(): List<FavouriteMovieEntity>

    //@Query("SELECT EXISTS (SELECT 1 FROM favourite_movies WHERE movie_id=:id)")
    //fun isFavourite(id: Int)

    @Delete(entity = FavouriteMovieEntity::class)
    fun deleteMovieId(favouriteMovieId: FavouriteMovieEntity)
}