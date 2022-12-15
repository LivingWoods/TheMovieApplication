package com.example.movieapplication.screens.searchmovie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapplication.database.movies.DatabaseMovie
import com.example.movieapplication.database.movies.MovieDatabaseDao
import com.example.movieapplication.domain.movie.DomainMovie
import kotlinx.coroutines.*
import timber.log.Timber

class SearchMovieViewModel(
    private val database: MovieDatabaseDao,
    application: Application
) :  AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private lateinit var _movies: MutableLiveData<List<DatabaseMovie>>
    private val _searchTerm = MutableLiveData<String>()
    private val _searchResults = MutableLiveData<List<DomainMovie>>()

    val searchTerm: LiveData<String>
        get() = _searchTerm
    val searchResults: LiveData<List<DomainMovie>>
        get() = _searchResults

    init {
        _searchTerm.value = ""
        initializeMovies()
    }

    private fun initializeMovies() {
        uiScope.launch {
            _movies = MutableLiveData(getAllMovies())
        }
    }

    private suspend fun getAllMovies(): List<DatabaseMovie> {
        return withContext(Dispatchers.IO) {
            // TODO: Remove this after implementing API calls
            database.deleteAll()
            database.insertAll(populateMovieData())
            database.getAll()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Method for retrieving movie data
     *
     * @param searchTerm The name of the movie that should be retrieved
     */
    fun fetchMovieData(searchTerm: String) {
        this._searchTerm.value = searchTerm

        _searchResults.value = _movies.value!!.filter {
            it.name.lowercase().contains(searchTerm.lowercase())
        }.map {
            DomainMovie(
                it.name,
                it.releaseDate,
                it.posterUrl,
                it.originalLanguage,
                it.synopsis
            )
        }
    }

    // TODO: Remove this after implementing API calls
    private fun populateMovieData(): List<DatabaseMovie> {
        return listOf(
            DatabaseMovie(
                1,
                "Frozen",
                "2013-11-20",
                "https://image.tmdb.org/t/p/w500/kgwjIb2JDHRhNk13lmSxiClFjVk.jpg",
                "en",
                "Young princess Anna of Arendelle dreams about finding true love at her sister Elsa’s coronation. Fate takes her on a dangerous journey in an attempt to end the eternal winter that has fallen over the kingdom. She's accompanied by ice delivery man Kristoff, his reindeer Sven, and snowman Olaf. On an adventure where she will find out what friendship, courage, family, and true love really means."
            ),
            DatabaseMovie(
                2,
                "Frozen II",
                "2019-11-20",
                "https://image.tmdb.org/t/p/w500/mINJaa34MtknCYl5AjtNJzWj8cD.jpg",
                "en",
                "Elsa, Anna, Kristoff and Olaf head far into the forest to learn the truth about an ancient mystery of their kingdom."
            ),
            DatabaseMovie(
                3,
                "First Blood",
                "1982-10-22",
                "https://image.tmdb.org/t/p/w500/a9sa6ERZCpplbPEO7OMWE763CLD.jpg",
                "en",
                "When former Green Beret John Rambo is harassed by local law enforcement and arrested for vagrancy, the Vietnam vet snaps, runs for the hills and rat-a-tat-tats his way into the action-movie hall of fame. Hounded by a relentless sheriff, Rambo employs heavy-handed guerilla tactics to shake the cops off his tail."
            )
        )
    }
}