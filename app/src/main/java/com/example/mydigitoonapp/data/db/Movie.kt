package com.example.mydigitoonapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mydigitoonapp.data.api.ResMovies
@Entity(tableName = "movies")
class Movie(
    var movie: ResMovies
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}

