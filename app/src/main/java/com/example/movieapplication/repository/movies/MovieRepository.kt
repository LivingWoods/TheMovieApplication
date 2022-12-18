package com.example.movieapplication.repository.movies

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.movieapplication.database.movies.MovieDatabase
import com.example.movieapplication.database.movies.asDomainModel
import com.example.movieapplication.domain.movies.DomainMovie
import com.example.movieapplication.network.movies.MovieApi
import com.example.movieapplication.network.movies.getResultsAsDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val database: MovieDatabase) {
    val movies = Transformations.map(database.movieDatabaseDao.getAll()) {
        it?.asDomainModel()
    }

    suspend fun refreshMovies(searchTerm: String) {
        withContext(Dispatchers.IO) {
            val fetchedMovies = MovieApi.retrofitService.searchMovie(searchTerm).await()
            database.movieDatabaseDao.deleteAll()
            database.movieDatabaseDao.insertAll(*fetchedMovies.getResultsAsDatabaseModel())
        }
    }
}