package com.example.mydigitoonapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mydigitoonapp.data.api.ResMovieDetail

@Entity(tableName = "movie_detail")
class MovieDetail(
    var movieDetail: ResMovieDetail
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}