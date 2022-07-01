package com.example.mydigitoonapp.data.api

import com.example.mydigitoontestapp.data.network.api.ApiService
import javax.inject.Inject

class ApiMoviesListRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getBatmanMoviesList() = apiService.getBatmanMoviesList()
    suspend fun getMovieDetail(imdbID:String) = apiService.getMovieDetail(imdbID)
}