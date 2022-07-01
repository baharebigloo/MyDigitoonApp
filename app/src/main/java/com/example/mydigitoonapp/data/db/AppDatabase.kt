package com.example.mydigitoonapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mydigitoonapp.util.MovieTypeConvertor

@Database(entities = [Movie::class], version = 1, exportSchema = true)
@TypeConverters(MovieTypeConvertor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}