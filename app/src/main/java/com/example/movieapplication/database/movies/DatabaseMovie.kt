package com.example.movieapplication.database.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class DatabaseMovie (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val releaseDate: String,
    val posterUrl: String,
    val originalLanguage: String,
    val synopsis: String,
)