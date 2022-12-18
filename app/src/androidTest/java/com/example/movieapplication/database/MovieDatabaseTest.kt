package com.example.movieapplication.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.movieapplication.database.movies.DatabaseMovie
import com.example.movieapplication.database.movies.MovieDatabase
import com.example.movieapplication.database.movies.MovieDatabaseDao
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MovieDatabaseTest {
    private lateinit var movieDatabaseDao: MovieDatabaseDao
    private lateinit var database: MovieDatabase

    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        database = Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        movieDatabaseDao = database.movieDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun destroyDatabase() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertMovieAndRetrieveMovie() {
        val movie = DatabaseMovie(
            1,
            "Frozen",
            "2013-11-20",
            "https://image.tmdb.org/t/p/w500/kgwjIb2JDHRhNk13lmSxiClFjVk.jpg",
            "en",
            "Young princess Anna of Arendelle dreams about finding true love at her sister Elsa’s coronation. Fate takes her on a dangerous journey in an attempt to end the eternal winter that has fallen over the kingdom. She's accompanied by ice delivery man Kristoff, his reindeer Sven, and snowman Olaf. On an adventure where she will find out what friendship, courage, family, and true love really means."
        )

        movieDatabaseDao.insert(movie)

        val retrievedMovie = movieDatabaseDao.getByMovieId(1)

        assertEquals(retrievedMovie?.name, "Frozen")
    }

    @Test
    @Throws(Exception::class)
    fun insertListOfMoviesAndRetrieveAllMovies() {
        val movies = listOf<DatabaseMovie>(
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

        movieDatabaseDao.insertAll(movies)

        val retrievedMovies = movieDatabaseDao.getAll()

        assertEquals(retrievedMovies.size, movies.size)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllMovies() {
        val movies = listOf<DatabaseMovie>(
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

        movieDatabaseDao.insertAll(movies)

        movieDatabaseDao.deleteAll()

        val retrievedMovies = movieDatabaseDao.getAll()

        assertEquals(retrievedMovies.size, 0)
    }
}