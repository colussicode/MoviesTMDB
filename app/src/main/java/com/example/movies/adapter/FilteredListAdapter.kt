package com.example.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.data.MovieSearchEntity

class FilteredListAdapter(
    private var dataset: List<MovieSearchEntity>
) : RecyclerView.Adapter<FilteredListAdapter.FilteredListViewHolder>() {

    class FilteredListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val filteredMovieTitle : TextView = view.findViewById(R.id.filter_text)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilteredListViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.filter_item, parent, false)

        return FilteredListViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: FilteredListViewHolder, position: Int) {
        holder.filteredMovieTitle.text = dataset[position].movieQuerySearch
    }


    override fun getItemCount() = dataset.size
}