package com.example.mydigitoonapp.data.db

import javax.inject.Inject

class DbMoviesListRepository @Inject constructor(private val movieDao : MovieDao) {
    fun getBatmanMoviesList() = movieDao.readMovies()
    suspend fun saveMoviesList(movie:Movie) = movieDao.insertMovies(movie)

}