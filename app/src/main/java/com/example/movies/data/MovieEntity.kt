package com.example.movies.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieSearches")
data class MovieEntity(
    @ColumnInfo(name = "movie_search") val movieSearch: String?,
    @PrimaryKey val id: Int
)