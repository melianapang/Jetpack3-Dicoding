package com.example.filmjetpacksub2.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.filmjetpacksub2.R
import com.example.filmjetpacksub2.databinding.FragmentFavoriteBinding
import com.example.filmjetpacksub2.ui.adapter.MovieAdapter
import com.example.filmjetpacksub2.ui.adapter.ShowAdapter
import com.example.filmjetpacksub2.ui.home.HomeFragment
import com.example.filmjetpacksub2.viewmodel.FavoriteViewModel
import com.example.filmjetpacksub2.viewmodel.ViewModelFactory

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favViewModel: FavoriteViewModel
    private var pos: Int = 0

    companion object {
        private const val POSITION = "position"

        @JvmStatic
        fun newInstance(position: Int) =
                FavoriteFragment().apply {
                    arguments = Bundle().apply {
                        putInt(POSITION, position)
                    }
                }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        showLoading(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        favViewModel = ViewModelProvider(
                this, factory
        ).get(FavoriteViewModel::class.java)

        pos = arguments?.getInt(HomeFragment.POSITION, 0) ?: 0

        when (pos) {
            0 -> initialiseRecyclerViewMovies()
            1 -> initialiseRecyclerViewShows()
        }
    }

    private fun initialiseRecyclerViewMovies() {
        val rvAdapter = MovieAdapter()
        with(binding.rvFavorite) {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = rvAdapter
        }

        favViewModel.getFavoriteFilm().observe(this, { listFilm ->
            if (listFilm.isNullOrEmpty()) {
                binding.tvAnnouncement.visibility = View.VISIBLE
                binding.tvAnnouncement.text = resources.getString(R.string.empty_favorite)
            }
            rvAdapter.submitList(listFilm)
            rvAdapter.notifyDataSetChanged()
            showLoading(false)
        })
    }

    private fun initialiseRecyclerViewShows() {
        val rvAdapter = ShowAdapter()
        with(binding.rvFavorite) {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = rvAdapter
        }

        favViewModel.getFavoriteTvShows().observe(this, { listShows ->
            if (listShows.isNullOrEmpty()) {
                binding.tvAnnouncement.visibility = View.VISIBLE
                binding.tvAnnouncement.text = resources.getString(R.string.empty_favorite)
            }
            rvAdapter.submitList(listShows)
            rvAdapter.notifyDataSetChanged()
            showLoading(false)
        })
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarFavorite.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }
}