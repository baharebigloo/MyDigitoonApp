package com.example.mydigitoontestapp.data.network.api

import com.example.mydigitoonapp.data.api.ResMovieDetail
import com.example.mydigitoonapp.data.api.ResMovies
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.HashMap


interface ApiService {

    //----------------------------------------------------------------------------------------------
    companion object {
        const val ENDPOINT = "https://www.omdbapi.com/"
    }
    //----------------------------------------------------------------------------------------------
    @GET("?apikey=3e974fca&s=batman")
    suspend fun getBatmanMoviesList(): Response<ResMovies>

    //----------------------------------------------------------------------------------------------
    @FormUrlEncoded
    @GET("?apikey=3e974fca")
    suspend fun getMovieDetail(@Field("imdbID") imdbID:String): Response<ResMovieDetail>
    //----------------------------------------------------------------------------------------------

}