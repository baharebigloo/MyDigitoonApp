package com.example.mydigitoonapp

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
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mydigitoonapp.data.db.Movie
import com.example.mydigitoonapp.databinding.ActivityMainBinding
import com.example.mydigitoonapp.ui.MoviesAdapter
import com.example.mydigitoonapp.ui.MoviesViewModel
import com.example.mydigitoonapp.ui.NetworkOfflineFragment
import com.example.mydigitoonapp.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //----------------------------------------------------------------------------------------------
    private lateinit var binding: ActivityMainBinding
    private val moviesViewModel: MoviesViewModel by viewModels()
    private lateinit var netWorkOffLineDialogFragment: NetworkOfflineFragment
    @Inject
    lateinit var moviesAdapter: MoviesAdapter

    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        netWorkOffLineDialogFragment = NetworkOfflineFragment()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)
        if (isInternetAvailable(this)) {
            provideData()
        } else {
            readCachedData()
        }
        //----------------------------------------------------------------
    }

    //----------------------------------------------------------------------------------------------
    private fun provideData() {
        moviesViewModel.getMovies().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d("TAGhhhhh", "onCreate: " + it.data!!.body()!!.search.size)
                    binding.recyclerView.isVisible = true
                    binding.progress.isVisible = false
                    moviesAdapter.dataList = it.data.body()!!.search
                    binding.recyclerView.adapter = moviesAdapter

                   val movieEntity = Movie(it.data.body()!!)
                    moviesViewModel.cacheMovies(movieEntity.movie)
                    Log.d("TAGppppp", "provideData: " + movieEntity.movie.search.size)
                }
                Status.ERROR -> {
                    Toast.makeText(
                        applicationContext,
                        "از سمت سرور خطایی رخ داده است",
                        Toast.LENGTH_LONG
                    ).show()
                }
                Status.LOADING -> {
                    binding.progress.isVisible = true
                    Log.d("TAGhhhhh", "onCreate3333: ")

                }
            }
        })
    }

    //----------------------------------------------------------------------------------------------
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

    private fun readCachedData() {
        lifecycleScope.launch {
            moviesViewModel.readMoviesLiveData.observe(this@MainActivity) { dataBase ->
                if (dataBase.isNotEmpty()) {
                    Log.d("TAGlllll", "readCachedData:() " + dataBase[0].movie.search.size)
                    moviesAdapter.dataList = dataBase[0].movie.search
                    binding.recyclerView.adapter = moviesAdapter
                } else {
                    // in case database is empty
                    Log.d("TAGlllll", "readCachedData:elseeeeeeee ")
                    netWorkOffLineDialogFragment.show(supportFragmentManager, "")
                    netWorkOffLineDialogFragment.setReloadListener {
                        if (isInternetAvailable( applicationContext)) {
                            netWorkOffLineDialogFragment.dismiss()
                            provideData()
                        }
                    }
                }
            }
        }
    }
    //----------------------------------------------------------------------------------------------
}