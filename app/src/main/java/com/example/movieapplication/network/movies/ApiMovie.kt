package com.example.movieapplication.network.movies

import com.example.movieapplication.database.movies.DatabaseMovie
import com.example.movieapplication.domain.movies.DomainMovie
import com.squareup.moshi.Json

data class ApiMovieResponse(
    val page: Int,
    val results: List<ApiMovie>
)

data class ApiMovie (
    val id: Int,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "overview")
    val synopsis: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "poster_path")
    val posterPath: String?
)

fun ApiMovieResponse.getResultsAsDatabaseModel() : Array<DatabaseMovie> {
    return this.results.map {
        DatabaseMovie(
            it.id,
            it.title,
            it.releaseDate,
            it.posterPath,
            it.originalLanguage,
            it.synopsis
        )
    }.toTypedArray()
}

fun ApiMovieResponse.getResultsAsDomainModel() : List<DomainMovie> {
    return this.results.map {
        DomainMovie(
            it.title,
            it.releaseDate,
            it.posterPath,
            it.originalLanguage,
            it.synopsis
        )
    }
}

fun ApiMovie.asDatabaseModel() : DatabaseMovie {
    return DatabaseMovie(
        this.id,
        this.title,
        this.releaseDate,
        this.posterPath,
        this.originalLanguage,
        this.synopsis
    )
}