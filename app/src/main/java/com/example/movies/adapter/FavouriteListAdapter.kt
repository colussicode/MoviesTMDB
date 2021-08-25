package com.example.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.data.FavouriteMovieEntity

class FavouriteListAdapter(
    private val dataset: List<FavouriteMovieEntity>
    ): RecyclerView.Adapter<FavouriteListAdapter.FavouriteViewHolder>() {

    class FavouriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movie_title: TextView = view.findViewById(R.id.movie_title)
        val movie_release_date: TextView = view.findViewById(R.id.movie_release_date)
        val movie_poster: ImageView = view.findViewById(R.id.movie_img)
        val movie_score: TextView = view.findViewById(R.id.movie_score)
        val fav_button: ToggleButton = view.findViewById(R.id.fav_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return FavouriteViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val IMG_BASE = "https://image.tmdb.org/t/p/w500/"

        Glide.with(holder.itemView)
            .load(IMG_BASE + dataset[position].poster_path)
            .into(holder.movie_poster)

        holder.movie_title.text = dataset[position].title
        holder.movie_release_date.text = dataset[position].release_date
        holder.movie_score.text = dataset[position].vote_average

        if (!dataset[position].isFavourite) holder.fav_button?.setBackgroundResource(R.drawable.filled_star)

    }

    override fun getItemCount() = dataset.size
}