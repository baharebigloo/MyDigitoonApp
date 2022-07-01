package com.example.mydigitoonapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("DELETE FROM movies")
    suspend fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movie: Movie)

    @Query("SELECT * FROM movies ORDER BY id ASC")
    fun readMovies(): Flow<List<Movie>>
}