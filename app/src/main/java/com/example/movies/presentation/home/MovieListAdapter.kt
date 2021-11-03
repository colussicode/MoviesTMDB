 package com.example.movies.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.data.models.MyMovie
import com.example.movies.R
import com.example.movies.databinding.ItemMovieCardBinding
import com.example.movies.utils.Constant.IMG_BASE_URL

 class MovieListAdapter(
    private val favouriteMovieListener: FavouriteMovieListener,
) : ListAdapter<MyMovie, MovieListAdapter.MovieViewHolder>(DiffMovie()) {

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
         val binding = ItemMovieCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
         return MovieViewHolder(binding)
     }

     override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
         holder.bind(getItem(position))
     }

     interface FavouriteMovieListener {
         fun onClickFavourite(myMovie: MyMovie)

         fun showDetails(id: Int)
     }

    inner class MovieViewHolder(private val binding: ItemMovieCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (item: MyMovie) = binding.run {
            Glide.with(itemView)
                .load(IMG_BASE_URL + item.poster_path)
                .into(movieImg)

            movieTitle.text = item.title
            movieReleaseDate.text = item.release_date
            movieScore.text = item.vote_average

            binding.root.setOnClickListener {
                favouriteMovieListener.showDetails(item.id)
            }

            if (item.isFavourite) {
                favButton.setBackgroundResource(R.drawable.filled_star)
            } else {
                favButton.setBackgroundResource(R.drawable.empty_star)
            }

            favButton.setOnCheckedChangeListener { _, _ ->
                item.run {
                    isFavourite = !isFavourite
                    notifyItemChanged(position)
                    favouriteMovieListener.onClickFavourite(this)
                }
            }
        }
    }
}

 class DiffMovie: DiffUtil.ItemCallback<MyMovie>() {
     override fun areItemsTheSame(oldItem: MyMovie, newItem: MyMovie): Boolean {
         return true
     }

     override fun areContentsTheSame(oldItem: MyMovie, newItem: MyMovie): Boolean {
         return oldItem == newItem
     }

 }