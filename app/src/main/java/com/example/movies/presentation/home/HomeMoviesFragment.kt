package com.example.movies.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.movies.data.local.getMovieDao
import com.example.movies.data.models.MyMovie
import com.example.movies.data.remote.repository.MovieRepositoryImp
import com.example.movies.databinding.FragmentHomeMoviesBinding

class HomeMoviesFragment : Fragment() {

    private lateinit var binding: FragmentHomeMoviesBinding
    private val viewModel by activityViewModels<HomeViewModel> {
        HomeViewModel.HomeViewModelFactory(
            MovieRepositoryImp(getMovieDao(requireContext()))
        )
    }

    private val adapter: MovieListAdapter by lazy {
        MovieListAdapter(object: MovieListAdapter.FavouriteMovieListener {
            override fun onClickFavourite(myMovie: MyMovie) {
                // TODO: Chamar o viewModel
            }

            override fun showDetails(id: Int) {
                // TODO: Show details
            }
        }).also { adapter ->
            binding.rvMovies.adapter = adapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservable()
    }


    private fun setObservable() {
        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.uiState.observe(viewLifecycleOwner) {
            when(it) {
                is UiState.Loading -> showLoading(it.isLoading)
                is UiState.Error -> {/*TODO:*/}
                UiState.Resume -> {/*TODO*/}
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.flip.displayedChild = if (isLoading) progress else layout
    }

    companion object {
        private const val layout = 0
        private const val progress = 1
    }
}