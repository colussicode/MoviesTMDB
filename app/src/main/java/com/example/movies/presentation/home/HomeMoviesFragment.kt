package com.example.movies.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.movies.data.models.MyMovie
import com.example.movies.databinding.FragmentHomeMoviesBinding

class HomeMoviesFragment : Fragment() {

    private lateinit var binding: FragmentHomeMoviesBinding
    private val viewModel: HomeViewModel by activityViewModels<HomeViewModel>()

    private val adapter: MovieListAdapter by lazy {
        MovieListAdapter(object: MovieListAdapter.FavouriteMovieListener {
            override fun onClickFavourite(myMovie: MyMovie) {
                // TODO: Chamar o viewModel
            }

            override fun showDetails(id: Int) {
                // TODO: Show details
            }
        })
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
        setView()
        setObservable()
    }

    private fun setView() = binding.run {
        rvMovies.adapter = adapter
    }

    private fun setObservable() {
        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}