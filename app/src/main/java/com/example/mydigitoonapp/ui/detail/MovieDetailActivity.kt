package com.example.mydigitoonapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.mydigitoonapp.databinding.ActivityMovieDetailBinding
import com.example.mydigitoonapp.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    //---------------------------------------------------------------------------------------
    private lateinit var binding: ActivityMovieDetailBinding
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()
    //------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        Log.d("TAGllllll", "onCreate: ")
        val movieId = intent.getStringExtra("imdbID")
        Log.d("TAGllllll", "onCreate: $movieId")
        provideData(movieId!!)
       /* title = intent.getStringExtra("title")
        imageUrl = intent.getStringExtra("imageUrl")
        date = intent.getStringExtra("date")
        Log.d("TAGpppppiii", "onCreate: $movieId")
        binding.toolbar.title = title
        binding.textYear.text = date
        Glide.with(binding.root.rootView.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_baseline_ondemand_video_24)
            .into(binding.imagePoster)*/
    }
    //---------------------------------------------------------------------------------------

    private fun provideData(imdbID:String) {
        movieDetailViewModel.getMovieDetail(imdbID).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.toolbar.title = it.data!!.body()!!.title
                    Log.d("TAGhhhhh", "onCreate: " + it.data.body()!!.language)
                }
                Status.ERROR -> {
                    Log.d("TAGhhhhh", "onCreate:error " + it.throwable!!.message)
                    Toast.makeText(
                        applicationContext,
                        "از سمت سرور خطایی رخ داده است",
                        Toast.LENGTH_LONG
                    ).show()
                }
                Status.LOADING -> {
                    Log.d("TAGhhhhh", "onCreate3333: ")

                }
            }
        })
    }
    //-------------------------------------------------------------------------------------------

}