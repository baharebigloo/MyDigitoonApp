package com.example.mydigitoonapp.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.mydigitoonapp.R
import com.example.mydigitoonapp.data.db.Movie
import com.example.mydigitoonapp.data.db.MovieDetail
import com.example.mydigitoonapp.databinding.ActivityMovieDetailBinding
import com.example.mydigitoonapp.ui.NetworkOfflineFragment
import com.example.mydigitoonapp.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    //---------------------------------------------------------------------------------------
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var netWorkOffLineDialogFragment: NetworkOfflineFragment
    lateinit var movieId: String
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()
    //------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        netWorkOffLineDialogFragment = NetworkOfflineFragment()
        movieId = intent.getStringExtra("imdbID").toString()
        if (isInternetAvailable(this)) {
            Log.d("TAGggggg", "onCreate:1111 ")
            provideData(movieId)
        } else {
            Log.d("TAGggggg", "onCreate:2222 ")
            readCachedData()
        }
    }
    //---------------------------------------------------------------------------------------

    @SuppressLint("SetTextI18n")
    private fun provideData(imdbID:String) {
        movieDetailViewModel.getMovieDetail(imdbID).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.container.visibility = View.VISIBLE
                    binding.progress.visibility = View.GONE
                    binding.toolbar.title = it.data!!.body()!!.title
                    binding.textYear.text = "Year:  "+it.data.body()!!.year
                    binding.textDirector.text = "Director:  "+it.data.body()!!.director
                    binding.textWriter.text = "Writer:  "+it.data.body()!!.writer
                    binding.textCountry.text = "Country:  "+ it.data.body()!!.country
                    binding.textType.text = "Type:  "+ it.data.body()!!.type
                    binding.textActors.text = "Actors:  "+ it.data.body()!!.actors
                    binding.textAwards.text = "Awards:  "+ it.data.body()!!.awards
                    Glide.with(binding.root.rootView.context)
                        .load(it.data.body()!!.poster)
                        .placeholder(R.drawable.ic_baseline_ondemand_video_24)
                        .into(binding.imagePoster)
                    //------------------------------------------------------------------------------
                    val movieDetailEntity = MovieDetail(it.data.body()!!)
                    movieDetailViewModel.cacheMovieDetail(movieDetailEntity.movieDetail)
                    Log.d("TAGhhhhhoooo", "onCreate: " + it.data.body()!!.language)
                }
                Status.ERROR -> {
                    Log.d("TAGhhhhhoooo", "onCreate:error " + it.throwable!!.message)
                    Toast.makeText(
                        applicationContext,
                        "از سمت سرور خطایی رخ داده است",
                        Toast.LENGTH_LONG
                    ).show()
                }
                Status.LOADING -> {
                    binding.container.visibility = View.GONE
                    binding.progress.visibility = View.VISIBLE
                    Log.d("TAGhhhhhoooo", "onCreate3333: ")

                }
            }
        })
    }
    //-------------------------------------------------------------------------------------------
    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        ConnectivityManager.TYPE_VPN -> true
                        else -> false
                    }
                }
            }
        }
        return result
    }
    //----------------------------------------------------------------------------------------------

    @SuppressLint("SetTextI18n")
    private fun readCachedData() {
        lifecycleScope.launch {
            movieDetailViewModel.readMovieDetailLiveData.observe(this@MovieDetailActivity) { dataBase ->
                if (!dataBase.movieDetail.equals(null)) {
                    binding.toolbar.title = dataBase.movieDetail.title
                    binding.textYear.text = "Year:  "+dataBase.movieDetail.year
                    binding.textDirector.text = "Director:  "+dataBase.movieDetail.director
                    binding.textWriter.text = "Writer:  "+dataBase.movieDetail.writer
                    binding.textCountry.text = "Country:  "+ dataBase.movieDetail.country
                    binding.textType.text = "Type:  "+ dataBase.movieDetail.type
                    binding.textActors.text = "Actors:  "+ dataBase.movieDetail.actors
                    binding.textAwards.text = "Awards:  "+ dataBase.movieDetail.awards
                    Glide.with(binding.root.rootView.context)
                        .load(dataBase.movieDetail.poster)
                        .placeholder(R.drawable.ic_baseline_ondemand_video_24)
                        .into(binding.imagePoster)
                } else {
                    // in case database is empty
                    Log.d("TAGlllll", "readCachedData:elseeeeeeee ")
                    netWorkOffLineDialogFragment.show(supportFragmentManager, "")
                    netWorkOffLineDialogFragment.setReloadListener {
                        if (isInternetAvailable( applicationContext)) {
                            netWorkOffLineDialogFragment.dismiss()
                            provideData(movieId)
                        }
                    }
                }
            }
        }
    }
    //----------------------------------------------------------------------------------------------

}