package com.example.mydigitoonapp.ui.detail

import androidx.lifecycle.*
import com.example.mydigitoonapp.data.api.ApiMoviesListRepository
import com.example.mydigitoonapp.data.api.ResMovieDetail
import com.example.mydigitoonapp.data.api.ResMovies
import com.example.mydigitoonapp.data.db.DbMoviesListRepository
import com.example.mydigitoonapp.data.db.Movie
import com.example.mydigitoonapp.data.db.MovieDetail
import com.example.mydigitoonapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val apiMovieListRepository: ApiMoviesListRepository,
    private val dbMovieListRepository: DbMoviesListRepository
) : ViewModel() {
    //----------------------------------------------------------------------------------------------
    val readMovieDetailLiveData: LiveData<MovieDetail> = readMovieDetail().asLiveData()

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

    private fun insertMovieDetail(movieDetail: MovieDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            dbMovieListRepository.saveMovieDetail(movieDetail)
        }
    }
    //----------------------------------------------------------------------------------------------
    private fun readMovieDetail(): Flow<MovieDetail> {
        return dbMovieListRepository.getBatmanMovieDetail()
    }
    //----------------------------------------------------------------------------------------------
    fun cacheMovieDetail(movieDetail: ResMovieDetail) {
        val movieDetailEntity = MovieDetail(movieDetail)
        insertMovieDetail(movieDetailEntity)
    }
    //----------------------------------------------------------------------------------------------
}