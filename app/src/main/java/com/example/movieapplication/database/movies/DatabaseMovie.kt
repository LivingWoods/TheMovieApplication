package com.example.movieapplication.database.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieapplication.domain.movies.DomainMovie

@Entity(tableName = "movie")
data class DatabaseMovie (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val releaseDate: String,
    val posterUrl: String?,
    val originalLanguage: String,
    val synopsis: String,
)

fun List<DatabaseMovie>.asDomainModel() : List<DomainMovie> {
    return map {
        DomainMovie(
            it.name,
            it.releaseDate,
            it.posterUrl,
            it.originalLanguage,
            it.synopsis
        )
    }
}