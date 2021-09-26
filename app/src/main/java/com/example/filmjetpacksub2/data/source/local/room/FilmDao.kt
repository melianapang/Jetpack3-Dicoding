package com.example.filmjetpacksub2.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.filmjetpacksub2.data.source.local.entity.MovieEntity
import com.example.filmjetpacksub2.data.source.local.entity.ShowEntity

@Dao
interface FilmDao {

    @Query("SELECT * FROM movieEntity")
    fun getAllMovies() : DataSource.Factory<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = MovieEntity::class)
    fun insertMovies(movies: ArrayList<MovieEntity>)

    @Query("SELECT * FROM showEntity")
    fun getAllShows() : DataSource.Factory<Int, ShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = ShowEntity::class)
    fun insertShows(shows: ArrayList<ShowEntity>)

    @Query("SELECT * FROM movieEntity WHERE movieId = :filmId and overview is not null and run_time is not null")
    fun getSelectedMovieById(filmId:Int): LiveData<MovieEntity>

    @Query("SELECT * FROM showEntity WHERE showId = :filmId  and overview is not null and episode_run_time is not null")
    fun getSelectedShowById(filmId:Int): LiveData<ShowEntity>

    @Update
    fun updateSelectedMovie(movie: MovieEntity)

    @Update
    fun updateSelectedShow(show: ShowEntity)

    @Query("SELECT * FROM movieEntity WHERE is_favorite = 1")
    fun getAllFavoriteMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM showEntity WHERE is_favorite = 1")
    fun getAllFavoriteShows(): DataSource.Factory<Int, ShowEntity>

    @Query("UPDATE movieEntity SET is_favorite = 1 WHERE movieId = :movieId")
    fun insertMovieFavorite(movieId: Int)

    @Query("UPDATE showEntity SET is_favorite = 1 WHERE showId = :showId")
    fun insertShowFavorite(showId: Int)

    @Query("UPDATE movieEntity SET is_favorite = 0 WHERE movieId = :filmId")
    fun deleteFavoriteMovieById(filmId: Int)

    @Query("UPDATE showEntity SET is_favorite = 0 WHERE showId = :filmId")
    fun deleteFavoriteShowById(filmId: Int)

    @Query("SELECT * FROM movieEntity WHERE title LIKE :name")
    fun searchAllMoviesByName(name: String): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM showEntity WHERE title LIKE :name")
    fun searchAllShowsByName(name: String): DataSource.Factory<Int, ShowEntity>
}