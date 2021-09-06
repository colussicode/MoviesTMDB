package com.example.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.adapter.FavouriteListAdapter
import com.example.movies.data.RoomSearchDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)
        recyclerView = findViewById(R.id.rv_favourite_movies_list)

        getFavouriteMovies()
    }

    private fun getFavouriteMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            val data = RoomSearchDataBase.getInstance(this@FavouriteActivity).movieDao()
                .getFavouriteMovies()

            val movieAdapter = FavouriteListAdapter(data.toMutableList())
            recyclerView.adapter = movieAdapter
        }
    }
}