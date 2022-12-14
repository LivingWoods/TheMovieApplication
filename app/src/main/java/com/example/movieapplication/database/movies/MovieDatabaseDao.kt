package com.example.movieapplication.database.movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDatabaseDao {
    @Insert
    fun insert(movie: DatabaseMovie)
    @Insert
    fun insertAll(movies: List<DatabaseMovie>)

    @Query("SELECT * FROM movie")
    fun getAll(): List<DatabaseMovie>
    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getByMovieId(movieId: Int): DatabaseMovie?
    @Query("DELETE FROM movie")
    fun deleteAll()
}