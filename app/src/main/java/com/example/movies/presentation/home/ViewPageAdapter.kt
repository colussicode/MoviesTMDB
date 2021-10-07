package com.example.movies.presentation.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movies.data.models.MovieGenre

class ViewPageAdapter(private val fragment: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragment, lifecycle){
    override fun getItemCount(): Int {
        return MovieGenre.values().count()
    }

    override fun createFragment(position: Int): Fragment {
        return fragment.findFragmentByTag("home") as HomeMoviesFragment
    }
}