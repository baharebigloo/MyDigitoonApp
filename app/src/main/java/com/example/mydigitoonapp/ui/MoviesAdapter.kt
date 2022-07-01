package com.example.mydigitoonapp.ui

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mydigitoonapp.R
import com.example.mydigitoonapp.data.api.SearchItem
import com.example.mydigitoonapp.databinding.MovieCardBinding
import com.example.mydigitoonapp.ui.detail.MovieDetailActivity
import java.util.*
import javax.inject.Inject

class MoviesAdapter @Inject constructor() : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
    var dataList: List<SearchItem> = ArrayList()

    //------------------------------------------------------------------------------------------------------
    class MoviesViewHolder(val binding: MovieCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movies: SearchItem) {
            binding.apply {
                title.text = movies.title
                val url: String = Objects.requireNonNull(movies.poster).toString()
                Glide.with(binding.root.rootView.context)
                    .load(url)
                    .placeholder(R.drawable.ic_baseline_ondemand_video_24)
                    .into(binding.thumbnail);

            }
        }
    }

    //-------------------------------------------------------------------------------------------------------
    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item: SearchItem = dataList[position]
        holder.binding.cardView.setOnClickListener {
            val intent = Intent(holder.binding.root.context,MovieDetailActivity::class.java)
            intent.putExtra("imdbID",item.imdbID)
            holder.binding.root.context.startActivity(intent)
            Log.d("TAGllllll", "onBindViewHolder: "+ item.imdbID)
            //   holder.binding.root.context.startActivity(Intent(holder.binding.root.context, MovieDetailActivity::class.java))
           /* MovieDetailActivity.start(
                holder.binding.root.context,
                item.imdbID
            )*/
        }
        holder.bind(item)
    }

    //------------------------------------------------------------------------------------------------------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding: MovieCardBinding =
            MovieCardBinding.inflate(LayoutInflater.from(parent.context))
        return MoviesViewHolder(binding)
    }

    //-------------------------------------------------------------------------------------------------------
    override fun getItemCount(): Int {
        return dataList.size
    }
    //------------------------------------------------------------------------------------------------------
}