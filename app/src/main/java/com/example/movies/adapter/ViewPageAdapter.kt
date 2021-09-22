package com.example.movies.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movies.fragments.ActionCategoryFragment
import com.example.movies.fragments.DramaCategoryFragment
import com.example.movies.fragments.FantasyCategoryFragment
import com.example.movies.fragments.WarCategoryFragment

class ViewPageAdapter(fragment: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragment, lifecycle){
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                ActionCategoryFragment()
            }
            1 -> {
                DramaCategoryFragment()
            }
            2 -> {
                FantasyCategoryFragment()
            }
            3 -> {
                WarCategoryFragment()
            }
            else -> {
                Fragment()
            }
        }
    }

}