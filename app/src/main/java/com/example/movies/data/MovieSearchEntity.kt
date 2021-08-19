package com.example.movies.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_searches")
data class MovieSearchEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "movie_id")
    val id: Int,

    @ColumnInfo(name = "typed_string")
    val movieQuerySearch: String?,
)