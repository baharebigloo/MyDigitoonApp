package com.example.mydigitoonapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.mydigitoonapp.data.api.ApiMoviesListRepository
import com.example.mydigitoonapp.data.db.DbMoviesListRepository
import com.example.mydigitoonapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val apiMovieListRepository: ApiMoviesListRepository,
    private val dbMovieListRepository: DbMoviesListRepository
) : ViewModel() {
    //----------------------------------------------------------------------------------------------
    fun getMovieDetail(imdbID:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiMovieListRepository.getMovieDetail(imdbID)))
        } catch (exception: Throwable) {
            emit(Resource.error(data = null, throwable = exception))
        }
    }
    //----------------------------------------------------------------------------------------------
}