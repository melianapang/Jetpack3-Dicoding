package com.example.filmjetpacksub2.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.filmjetpacksub2.data.source.local.entity.MovieEntity
import com.example.filmjetpacksub2.data.source.local.entity.ShowEntity
import com.example.filmjetpacksub2.value_object.Resource

interface IDataSource {
    fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getAllShows(): LiveData<Resource<PagedList<ShowEntity>>>

    fun getSelectedTvShow(showId: Int): LiveData<Resource<ShowEntity>>
    fun getSelectedMovie(movieId: Int): LiveData<Resource<MovieEntity>>

    fun getAllFavoriteShows(): LiveData<PagedList<ShowEntity>>
    fun getAllFavoriteMovies(): LiveData<PagedList<MovieEntity>>

    fun setFavoriteMovie(movieId: Int)
    fun setFavoriteShow(showId: Int)

    fun deleteFavoriteMovie(filmId: Int)
    fun deleteFavoriteShow(filmId: Int)

    fun searchAllMovies(name:String): LiveData<PagedList<MovieEntity>>
    fun searchAllShows(name:String): LiveData<PagedList<ShowEntity>>
}