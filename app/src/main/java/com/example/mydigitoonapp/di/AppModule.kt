package com.example.mydigitoonapp.di

import android.content.Context
import androidx.room.Room
import com.example.mydigitoonapp.data.db.AppDatabase
import com.example.mydigitoonapp.data.db.MovieDao
import com.example.mydigitoontestapp.data.network.api.ApiService
import com.example.mydigitoontestapp.data.network.api.ApiService.Companion.ENDPOINT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //----------------------------------------------------------------------------------------------
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    //----------------------------------------------------------------------------------------------
    @Provides
    @Singleton
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(15, TimeUnit.SECONDS) // connect timeout
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }
    //----------------------------------------------------------------------------------------------
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    //----------------------------------------------------------------------------------------------
    @Provides
    @Singleton
    fun provideMovieAppService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
    //----------------------------------------------------------------------------------------------
    //instance of AppDatabase
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "RssReader"
        ).build()
    }
    //----------------------------------------------------------------------------------------------
    //instance of movieDao
    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }
    //----------------------------------------------------------------------------------------------
}