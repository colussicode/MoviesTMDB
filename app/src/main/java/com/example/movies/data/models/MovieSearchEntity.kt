package com.example.movies.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "movie_searches", indices = [Index(value = ["typed_string"], unique = true)])
data class MovieSearchEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "typed_string")
    val movieQuerySearch: String,
)