package com.example.filmjetpacksub2.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.filmjetpacksub2.data.source.local.entity.MovieEntity
import com.example.filmjetpacksub2.data.source.local.entity.ShowEntity
import com.example.filmjetpacksub2.data.source.local.room.FilmDao

class LocalDataSource private constructor(private val filmDao: FilmDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(filmDao: FilmDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(filmDao)
    }

    fun getAllMovies() : DataSource.Factory<Int, MovieEntity> = filmDao.getAllMovies()

    fun insertMovies(movies: ArrayList<MovieEntity>) = filmDao.insertMovies(movies)

    fun getAllShows() : DataSource.Factory<Int, ShowEntity> = filmDao.getAllShows()

    fun insertShows(shows: ArrayList<ShowEntity>) = filmDao.insertShows(shows)

    fun getSelectedMovieById(filmId: Int): LiveData<MovieEntity> = filmDao.getSelectedMovieById(filmId)

    fun getSelectedShowById(filmId: Int): LiveData<ShowEntity> = filmDao.getSelectedShowById(filmId)

    fun updateSelectedShow(show: ShowEntity) = filmDao.updateSelectedShow(show)

    fun updateSelectedMovie(movie: MovieEntity) = filmDao.updateSelectedMovie(movie)

    fun getAllFavoriteMovies(): DataSource.Factory<Int, MovieEntity> = filmDao.getAllFavoriteMovies()

    fun getAllFavoriteShows(): DataSource.Factory<Int, ShowEntity> = filmDao.getAllFavoriteShows()

    fun insertMovieFavoriteById(movieId: Int) = filmDao.insertMovieFavorite(movieId)

    fun insertShowFavoriteById(showId: Int) = filmDao.insertShowFavorite(showId)

    fun deleteFavoriteMovieById(filmId: Int) = filmDao.deleteFavoriteMovieById(filmId)

    fun deleteFavoriteShowById(filmId: Int) = filmDao.deleteFavoriteShowById(filmId)

    fun searchAllMovies(name:String): DataSource.Factory<Int, MovieEntity> = filmDao.searchAllMoviesByName(name)

    fun searchAllShows(name:String): DataSource.Factory<Int, ShowEntity> = filmDao.searchAllShowsByName(name)
}