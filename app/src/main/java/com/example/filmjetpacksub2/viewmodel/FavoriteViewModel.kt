package com.example.filmjetpacksub2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.filmjetpacksub2.data.Repository
import com.example.filmjetpacksub2.data.source.local.entity.MovieEntity
import com.example.filmjetpacksub2.data.source.local.entity.ShowEntity

    class FavoriteViewModel(private val repository: Repository) : ViewModel()  {
        fun getFavoriteFilm(): LiveData<PagedList<MovieEntity>> = repository.getAllFavoriteMovies()
        fun getFavoriteTvShows(): LiveData<PagedList<ShowEntity>> = repository.getAllFavoriteShows()
    }