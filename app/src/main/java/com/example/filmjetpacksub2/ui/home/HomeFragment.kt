package com.example.filmjetpacksub2.ui.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.filmjetpacksub2.R
import com.example.filmjetpacksub2.databinding.FragmentHomeBinding
import com.example.filmjetpacksub2.ui.adapter.MovieAdapter
import com.example.filmjetpacksub2.ui.adapter.ShowAdapter
import com.example.filmjetpacksub2.value_object.Status
import com.example.filmjetpacksub2.viewmodel.HomeViewModel
import com.example.filmjetpacksub2.viewmodel.ViewModelFactory


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private var pos: Int = 0

    companion object {
        const val POSITION = "position"

        @JvmStatic
        fun newInstance(position: Int) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION, position)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        showLoading(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        homeViewModel = ViewModelProvider(
            this, factory
        ).get(HomeViewModel::class.java)

        pos = arguments?.getInt(POSITION, 0) ?: 0

        when (pos) {
            0 -> initialiseRecyclerViewMovies()
            1 -> initialiseRecyclerViewShows()
        }
    }


    private fun initialiseRecyclerViewMovies() {
        val rvAdapter = MovieAdapter()
        with(binding.rvHome) {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = rvAdapter
        }

        homeViewModel.getFilm().observe(this, { listFilm ->
            if (listFilm != null) {
                when (listFilm.status) {
                    Status.LOADING -> showLoading(true)
                    Status.SUCCESS -> {
                        rvAdapter.submitList(listFilm.data)
                        rvAdapter.notifyDataSetChanged()
                        showLoading(false)
                    }
                    Status.ERROR -> {
                        showLoading(false)
                        showToast(resources.getString(R.string.error_nointernet))
                    }
                }
            }
        })
        searchViewManager(rvAdapter,null)
    }

    private fun initialiseRecyclerViewShows(){
        val rvAdapter = ShowAdapter()
        with(binding.rvHome) {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = rvAdapter
        }

        homeViewModel.getTvShows().observe(this, { listShows ->
            if (listShows != null) {
                when (listShows.status) {
                    Status.LOADING -> showLoading(true)
                    Status.SUCCESS -> {
                        rvAdapter.submitList(listShows.data)
                        rvAdapter.notifyDataSetChanged()
                        showLoading(false)
                    }
                    Status.ERROR -> {
                        showLoading(false)
                        showToast(resources.getString(R.string.error_nointernet))
                    }
                }
            }
        })
        searchViewManager(null,rvAdapter)
    }

    private fun searchViewManager(movieAdapter: MovieAdapter?, showAdapter: ShowAdapter?) {
        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        binding.searchView.queryHint = resources.getString(R.string.search_hint)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) observeFilterData(query,movieAdapter,showAdapter)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) observeFilterData(newText,movieAdapter,showAdapter)
                return false
            }
        })
    }

    private fun observeFilterData(text: String, movieAdapter: MovieAdapter?,showAdapter: ShowAdapter?) {
        showLoading(true)
        val queryName = StringBuilder().append("%").append(text).append("%").toString()
        when(pos){
            0 -> {
                homeViewModel.searchFilm(queryName).observe(requireActivity(), { films ->
                    movieAdapter?.submitList(films)
                    movieAdapter?.notifyDataSetChanged()
                    if (films.isEmpty()) {
                        binding.tvSearchResult.visibility = View.VISIBLE
                    } else {
                        binding.tvSearchResult.visibility = View.INVISIBLE
                    }
                })
            }
            1->{
                homeViewModel.searchTvShows(queryName).observe(requireActivity(), { films ->
                    showAdapter?.submitList(films)
                    showAdapter?.notifyDataSetChanged()
                    if (films.isEmpty()) {
                        binding.tvSearchResult.visibility = View.VISIBLE
                    } else {
                        binding.tvSearchResult.visibility = View.INVISIBLE
                    }
                })
            }
        }
        showLoading(false)
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    private fun showToast(msg: String){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}