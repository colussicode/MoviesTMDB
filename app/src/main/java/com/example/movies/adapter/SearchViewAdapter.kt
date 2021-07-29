package com.example.movies.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.MyMovie

class SearchViewAdapter(
    private val dataset: List<MyMovie>
) : RecyclerView.Adapter<SearchViewAdapter.SearchViewHolder>(){

    class SearchViewHolder(private val view) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = dataset.size
}