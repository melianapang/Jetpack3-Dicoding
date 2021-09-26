package com.example.filmjetpacksub2.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapterFavorite(activity: AppCompatActivity) : FragmentStateAdapter(activity)  {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return FavoriteFragment.newInstance(position)
    }
}