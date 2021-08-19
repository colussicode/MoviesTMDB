package com.example.movies.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_movies")
data class FavouriteMovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movie_id")
    val id: Int,
)