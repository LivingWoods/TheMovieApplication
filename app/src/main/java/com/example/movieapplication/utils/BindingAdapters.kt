package com.example.movieapplication.utils

import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.movieapplication.R
import com.example.movieapplication.domain.movies.DomainMovie
import java.text.SimpleDateFormat
import java.time.LocalDate

/**
 * BindingAdapter Method for handling the loading of the movie posters
 */
@BindingAdapter("moviePoster")
fun ImageView.setMoviePoster(url: String?) {
    val mainAddress = "https://image.tmdb.org/t/p/w500"
    val posterUrl = "$mainAddress$url"

    url?.let {
        Glide.with(context).load(posterUrl).placeholder(R.mipmap.film_poster_placeholder).into(this)
    }
}

@BindingAdapter("movieName")
fun TextView.setMovieName(movieName: String) {
    this.text = movieName
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("movieReleaseDate")
fun TextView.setMovieReleaseDate(releaseDate: String) {
    this.text = LocalDate.parse(releaseDate).toString()
}

@BindingAdapter("movieSynopsis")
fun TextView.setSynopsis(synopsis: String) {
    this.text = synopsis
}