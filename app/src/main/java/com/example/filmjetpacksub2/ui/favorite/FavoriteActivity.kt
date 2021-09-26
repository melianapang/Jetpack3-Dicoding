package com.example.filmjetpacksub2.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.filmjetpacksub2.R
import com.example.filmjetpacksub2.databinding.ActivityFavoriteBinding
import com.example.filmjetpacksub2.ui.home.MainActivity
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.movies_tab,
            R.string.tvshows_tab
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managePagerAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu_favorite, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home_page) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        return true
    }

    private fun managePagerAdapter() {
        val sectionsPagerAdapter = SectionPagerAdapterFavorite(this)
        binding.viewPagerFavorite.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabLayoutFavorite, binding.viewPagerFavorite) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
        supportActionBar?.title = resources.getString(R.string.favorite_page)
    }
}