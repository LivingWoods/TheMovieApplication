package com.example.movieapplication.screens.searchmovie

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.movieapplication.database.movies.DatabaseMovie
import com.example.movieapplication.database.movies.MovieDatabase
import com.example.movieapplication.database.movies.MovieDatabaseDao
import com.example.movieapplication.domain.movies.DomainMovie
import com.example.movieapplication.network.movies.ApiMovieResponse
import com.example.movieapplication.network.movies.MovieApi
import com.example.movieapplication.repository.movies.MovieRepository
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMovieViewModel(
    application: Application
) :  AndroidViewModel(application) {
    private val database = MovieDatabase.getInstance(application.applicationContext)
    private val movieRepository = MovieRepository(database)

    private val _searchTerm = MutableLiveData<String>()
    private var _searchResults: LiveData<List<DomainMovie>?>

    val searchTerm: LiveData<String>
        get() = _searchTerm
    val searchResults: LiveData<List<DomainMovie>?>
        get() = _searchResults

    init {
        _searchTerm.value = ""
        _searchResults = movieRepository.movies
    }

    /**
     * Method for retrieving movie data
     *
     * @param searchTerm The name of the movie that should be retrieved
     */
    fun fetchMovieData(searchTerm: String) {
        this._searchTerm.value = searchTerm
        searchMovie(searchTerm)
    }

    private fun searchMovie(searchTerm: String) {
        viewModelScope.launch {
            movieRepository.refreshMovies(searchTerm)
        }
    }

    class Factory(
        private val application: Application
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchMovieViewModel::class.java)) {
                return SearchMovieViewModel(application) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}