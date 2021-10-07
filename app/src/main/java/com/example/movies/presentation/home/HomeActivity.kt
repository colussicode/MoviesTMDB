package com.example.movies.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.example.movies.R
import com.example.movies.data.local.RoomSearchDataBase
import com.example.movies.data.models.MovieGenre
import com.example.movies.data.models.MyMovie
import com.example.movies.data.remote.repository.MovieRepositoryImp
import com.example.movies.databinding.GetMoviesBinding
import com.example.movies.presentation.details.DetailsActivity
import com.example.movies.presentation.favourite.FavouriteActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.*


const val BASE_URL = "https://api.themoviedb.org/3/"

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: GetMoviesBinding
    private val viewModel: HomeViewModel by viewModels() {
        // TODO: Implementar Injeção de Depêndencia
        HomeViewModel.HomeViewModelFactory(
            MovieRepositoryImp(
                RoomSearchDataBase.getInstance(
                    this
                ).movieDao()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GetMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViews()
        //setTabLayout()
        setListeners()
    }

    private fun setViews() = binding.run {
        supportFragmentManager.commit {
            replace<HomeMoviesFragment>(listFragment.id, "home")
        }
    }

    private fun setObservables() = binding.run {
        viewModel.uiState.observe(this@HomeActivity) {
            when (it) {
                is UiState.Error -> {
                }
                UiState.Loading -> {
                }
                UiState.Resume -> {
                }
            }
        }
        viewModel.movies.observe(this@HomeActivity) {

        }
    }

    private fun setTabLayout() = binding.run {
        val adapter = ViewPageAdapter(supportFragmentManager, lifecycle)

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            MovieGenre.values().find {
                it.ordinal == position
            }?.let {
                tab.text = it.name
            }
        }.attach()
    }

    private fun setListeners() = binding.run {
        favouriteButton.setOnClickListener { goToFavouriteMovies() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val searchItem = menu?.findItem(R.id.nvar_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {


            override fun onQueryTextSubmit(query: String?): Boolean {
                // TODO: Criar e implementar método no viewmodel para buscar dados no RoomDatabase
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // TODO: Criar e implementar método no viewmodel para buscar dados no RoomDatabase
                return true
            }
        })
        return true
    }


    private fun goToFavouriteMovies() {
        val context = baseContext
        val intent = Intent(context, FavouriteActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    private fun onClickFavourite(myMovie: MyMovie) {
        // TODO: Criar e implementar método no viewmodel para buscar dados no RoomDatabase
    }

    private fun showDetails(id: Int) {
        val context = baseContext

        val intent = Intent(context, DetailsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("id", id)
        context.startActivity(intent)
    }
}