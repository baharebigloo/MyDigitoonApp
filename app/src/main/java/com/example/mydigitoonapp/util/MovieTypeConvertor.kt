package com.example.mydigitoonapp.util

import androidx.room.TypeConverter
import com.example.mydigitoonapp.data.api.ResMovies
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MovieTypeConvertor {

    val gson = Gson()

    @TypeConverter
    fun movieToString(movie: ResMovies): String {
        return gson.toJson(movie)
    }

    @TypeConverter
    fun stringToMovie(movieString: String): ResMovies {
        val objectType = object : TypeToken<ResMovies>() {}.type
        return gson.fromJson(movieString, objectType)
    }
}