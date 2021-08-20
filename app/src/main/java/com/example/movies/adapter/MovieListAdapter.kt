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
import com.example.movies.DetailsActivity
import com.example.movies.MyMovie
import com.example.movies.R
import com.example.movies.data.RoomSearchDataBase


 class MovieListAdapter(
    private val dataset: List<MyMovie>,
    private val favouriteMovieListener: FavouriteMovieListener
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>(){

     interface FavouriteMovieListener {
         fun onClickFavourite (movieId: Int, isFavourite: Boolean)
     }

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
            .load(IMG_BASE + dataset[position].poster_path)
            .into(holder.movie_poster)

        holder.movie_title.text = dataset[position].title
        holder.movie_release_date.text = dataset[position].release_date
        holder.movie_score.text = dataset[position].vote_average

        holder.view.setOnClickListener {
            val context = holder.view.context
            val intent = Intent(context, DetailsActivity::class.java)

            intent.putExtra("id", dataset[position].id)
            context.startActivity(intent)
        }

        val movieId = dataset[position].id


        if (dataset[position].isFavourite) {
            holder.fav_button?.setBackgroundResource(R.drawable.filled_star)
        } else {
            holder.fav_button?.setBackgroundResource(R.drawable.empty_star)
        }

        holder.fav_button.setOnCheckedChangeListener { buttonView, isChecked ->
            dataset[position].isFavourite = !dataset[position].isFavourite
            notifyItemChanged(position)
            favouriteMovieListener.onClickFavourite(movieId, isChecked)
        }
    }

    override fun getItemCount() = dataset.size
}