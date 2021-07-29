package com.example.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.MyMovie
import com.example.movies.R

class SearchViewAdapter(
    private val movies: List<MyMovie>
) : RecyclerView.Adapter<SearchViewAdapter.SearchViewHolder>(){

    class SearchViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val movie_title: TextView = view.findViewById(R.id.movie_title)
        val movie_release_date: TextView = view.findViewById(R.id.movie_release_date)
        val movie_poster: ImageView = view.findViewById(R.id.movie_img)
        val movie_score: TextView = view.findViewById(R.id.movie_score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_movies, parent, false)

        return SearchViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val IMG_BASE = "https://image.tmdb.org/t/p/w500/"

        Glide.with(holder.itemView)
            .load(IMG_BASE + movies[position].poster_path)
            .into(holder.movie_poster)

        holder.movie_title.text = movies[position].title
        holder.movie_release_date.text = movies[position].release_date
        holder.movie_score.text = movies[position].vote_average
    }

    override fun getItemCount() = movies.size
}