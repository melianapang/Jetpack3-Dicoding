package com.example.filmjetpacksub2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.filmjetpacksub2.data.Repository
import com.example.filmjetpacksub2.data.source.local.entity.MovieEntity
import com.example.filmjetpacksub2.data.source.local.entity.ShowEntity
import com.example.filmjetpacksub2.value_object.Resource

class DetailViewModel(private val repository: Repository) : ViewModel() {
    private var movieId: Int = 0
    private var showId: Int = 0

    fun setSelectedMovie(movieId: Int) {
        this.movieId = movieId
    }

    fun setSelectedTvShow(showId: Int) {
        this.showId = showId
    }

    fun getSelectedMovie(): LiveData<Resource<MovieEntity>> = repository.getSelectedMovie(movieId)
    fun getSelectedTvShow(): LiveData<Resource<ShowEntity>> = repository.getSelectedTvShow(showId)

    fun setFavoriteMovie(movieId: Int) = repository.setFavoriteMovie(movieId)
    fun setFavoriteShow(showId : Int) = repository.setFavoriteShow(showId)

    fun deleteFavoriteMovie(movieId: Int) = repository.deleteFavoriteMovie(movieId)
    fun deleteFavoriteShow(showId : Int) = repository.deleteFavoriteShow(showId)
}

