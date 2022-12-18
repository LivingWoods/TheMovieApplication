package com.example.movieapplication.database.movies

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDatabaseDao {
    @Insert
    fun insert(movie: DatabaseMovie)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movies: DatabaseMovie)

    @Query("SELECT * FROM movie")
    fun getAll(): LiveData<List<DatabaseMovie>?>
    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getByMovieId(movieId: Int): LiveData<DatabaseMovie?>
    @Query("DELETE FROM movie")
    fun deleteAll()
}