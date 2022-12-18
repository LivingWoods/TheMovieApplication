package com.example.movieapplication.domain.movies

data class DomainMovie(
    val name: String,
    val releaseDate: String,
    val posterUrl: String?,
    val originalLanguage: String,
    val synopsis: String
)