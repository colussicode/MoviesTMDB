package com.example.movies.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.MyMovie
import com.example.movies.R

class FavouriteListAdapter(
    private val dataset: List<MyMovie>,

    ): RecyclerView.Adapter<FavouriteListAdapter.FavouriteViewHolder>() {

    class FavouriteViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val movie_title: TextView = view.findViewById(R.id.movie_title)
        val movie_release_date: TextView = view.findViewById(R.id.movie_release_date)
        val movie_poster: ImageView = view.findViewById(R.id.movie_img)
        val movie_score: TextView = view.findViewById(R.id.movie_score)
        val fav_button: ToggleButton = view.findViewById(R.id.fav_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = dataset.size
}