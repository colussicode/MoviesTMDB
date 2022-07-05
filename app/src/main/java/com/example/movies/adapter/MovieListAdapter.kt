 package com.example.movies.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.domain.model.Movie


 class MovieListAdapter(
    private val dataset: List<Movie>,
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>(){


    class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val movie_title: TextView = view.findViewById(R.id.movie_title)
        val movie_release_date: TextView = view.findViewById(R.id.movie_release_date)
        val movie_poster: ImageView = view.findViewById(R.id.movie_img)
        val movie_score: TextView = view.findViewById(R.id.movie_score)
        val fav_button: ToggleButton = view.findViewById(R.id.fav_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val IMG_BASE = "https://image.tmdb.org/t/p/w500/"

        Glide.with(holder.itemView)
            .load(IMG_BASE + dataset[position].id)
            .into(holder.movie_poster)

        holder.movie_title.text = dataset[position].title
        holder.movie_release_date.text = dataset[position].releaseDate

        holder.view.setOnClickListener {
            val context = holder.view.context

        }

        val movieId = dataset[position].id
        val movieTitle = dataset[position].title
        val movieReleaseDate = dataset[position].releaseDate
        val movieVoteAverage = dataset[position].voteAverage

    }

    override fun getItemCount() = dataset.size
}