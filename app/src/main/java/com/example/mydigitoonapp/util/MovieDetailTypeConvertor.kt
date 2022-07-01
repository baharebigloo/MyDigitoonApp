package com.example.mydigitoonapp.util

import androidx.room.TypeConverter
import com.example.mydigitoonapp.data.api.ResMovieDetail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MovieDetailTypeConvertor {

    val gson = Gson()

    @TypeConverter
    fun movieDetailToString(movieDetail: ResMovieDetail): String {
        return gson.toJson(movieDetail)
    }

    @TypeConverter
    fun stringToMovieDetail(movieDetailString: String): ResMovieDetail {
        val objectType = object : TypeToken<ResMovieDetail>() {}.type
        return gson.fromJson(movieDetailString, objectType)
    }
}