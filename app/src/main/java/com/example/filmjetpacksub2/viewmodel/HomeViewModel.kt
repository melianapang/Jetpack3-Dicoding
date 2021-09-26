package com.example.filmjetpacksub2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.filmjetpacksub2.data.Repository
import com.example.filmjetpacksub2.data.source.local.entity.MovieEntity
import com.example.filmjetpacksub2.data.source.local.entity.ShowEntity
import com.example.filmjetpacksub2.value_object.Resource

class HomeViewModel(private val repository: Repository) : ViewModel() {
    fun getFilm(): LiveData<Resource<PagedList<MovieEntity>>> = repository.getAllMovies()
    fun getTvShows(): LiveData<Resource<PagedList<ShowEntity>>> = repository.getAllShows()

    fun searchFilm(name:String): LiveData<PagedList<MovieEntity>> = repository.searchAllMovies(name)
    fun searchTvShows(name:String): LiveData<PagedList<ShowEntity>> = repository.searchAllShows(name)
}