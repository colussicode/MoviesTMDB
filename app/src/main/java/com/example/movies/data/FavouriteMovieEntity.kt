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

    @ColumnInfo(name = "movie_title")
    val title: String,

    @ColumnInfo(name = "release_date")
    val release_date: String,

    @ColumnInfo(name = "vote_average")
    val vote_average: String,

    @ColumnInfo(name = "poster_path")
    val poster_path: String,

    @ColumnInfo(name = "is_favourite")
    var isFavourite: Boolean = false
)