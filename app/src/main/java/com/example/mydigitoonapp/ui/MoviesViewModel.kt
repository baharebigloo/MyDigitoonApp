package com.example.mydigitoonapp.ui

import androidx.lifecycle.*
import com.example.mydigitoonapp.data.api.ApiMoviesListRepository
import com.example.mydigitoonapp.data.api.ResMovies
import com.example.mydigitoonapp.data.api.SearchItem
import com.example.mydigitoonapp.data.db.DbMoviesListRepository
import com.example.mydigitoonapp.data.db.Movie
import com.example.mydigitoonapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val apiMovieListRepository: ApiMoviesListRepository,
    private val dbMovieListRepository: DbMoviesListRepository
) : ViewModel() {

    val readMoviesLiveData: LiveData<List<Movie>> = readMovies().asLiveData()

    //----------------------------------------------------------------------------------------------
    fun getMovies() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiMovieListRepository.getBatmanMoviesList()))
        } catch (exception: Throwable) {
            emit(Resource.error(data = null, throwable = exception))
        }
    }

    //----------------------------------------------------------------------------------------------
    private fun insertMovies(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            dbMovieListRepository.saveMoviesList(movie)
        }
    }
    //----------------------------------------------------------------------------------------------
    private fun readMovies(): Flow<List<Movie>> {
        return dbMovieListRepository.getBatmanMoviesList()
    }
    //----------------------------------------------------------------------------------------------
      fun cacheMovies(movie: ResMovies) {
        val moviesEntity = Movie(movie)
        insertMovies(moviesEntity)
    }
    //----------------------------------------------------------------------------------------------
}