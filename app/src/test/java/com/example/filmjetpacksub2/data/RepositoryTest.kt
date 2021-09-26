package com.example.filmjetpacksub2.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.filmjetpacksub2.data.source.local.LocalDataSource
import com.example.filmjetpacksub2.data.source.local.entity.MovieEntity
import com.example.filmjetpacksub2.data.source.local.entity.ShowEntity
import com.example.filmjetpacksub2.data.source.remote.RemoteDataSource
import com.example.filmjetpacksub2.utils.*
import com.example.filmjetpacksub2.value_object.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class RepositoryTest {
    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val testExecutors = AppExecutors(TestExecutor(), TestExecutor(), TestExecutor())

    private val repository = FakeRepository(remote, local, appExecutors)

    private val moviesResponses = DataDummy.generateDummyMovie()
    private val movieId = moviesResponses[0].id
    private val showsResponses = DataDummy.generateDummyTvShow()
    private val showId = showsResponses[0].id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testGetFilm() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        repository.getAllMovies()

        val movies = Resource.success(PageListUtil.mockPagedList(DataDummy.generateDummyMovie()))
        verify(local).getAllMovies()
        assertNotNull(movies.data)
        assertEquals(moviesResponses.size.toLong(), movies.data?.size?.toLong())
    }

    @Test
    fun testGetTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, ShowEntity>
        `when`(local.getAllShows()).thenReturn(dataSourceFactory)
        repository.getAllShows()

        val shows = Resource.success(PageListUtil.mockPagedList(DataDummy.generateDummyTvShow()))
        verify(local).getAllShows()
        assertNotNull(shows.data)
        assertEquals(showsResponses.size.toLong(), shows.data?.size?.toLong())
    }

    @Test
    fun testGetSelectedMovie() {
        val movieObject = DataDummy.generateDummyMovie()[0]
        val dummyEntity = MutableLiveData<MovieEntity>()
        dummyEntity.value = movieObject

        `when`(local.getSelectedMovieById(movieId)).thenReturn(dummyEntity)
        val movie = LiveDataTestUtils.getValue(repository.getSelectedMovie(movieId))
        verify(local).getSelectedMovieById(movieId)

        assertNotNull(movie.data)
        assertEquals(movieObject.id, movie.data?.id)
        assertEquals(movieObject.title, movie.data?.title)
        assertEquals(movieObject.runTime, movie.data?.runTime)
        assertEquals(movieObject.overview, movie.data?.overview)
        assertEquals(movieObject.genres, movie.data?.genres)
        assertEquals(movieObject.releaseDate, movie.data?.releaseDate)
    }

    @Test
    fun testGetSelectedTvShow() {
        val showObject = DataDummy.generateDummyTvShow()[0]
        val dummyEntity = MutableLiveData<ShowEntity>()
        dummyEntity.value = showObject

        `when`(local.getSelectedShowById(showId)).thenReturn(dummyEntity)
        val show = LiveDataTestUtils.getValue(repository.getSelectedTvShow(showId))
        verify(local).getSelectedShowById(showId)

        assertNotNull(show.data)
        assertEquals(showObject.id, show.data?.id)
        assertEquals(showObject.title, show.data?.title)
        assertEquals(showObject.runTime, show.data?.runTime)
        assertEquals(showObject.overview, show.data?.overview)
        assertEquals(showObject.genres, show.data?.genres)
        assertEquals(showObject.firstAirDate, show.data?.firstAirDate)
        assertEquals(showObject.numOfEps, show.data?.numOfEps)
        assertEquals(showObject.numOfSeasons, show.data?.numOfSeasons)
    }

    @Test
    fun testGetAllFavoriteMovie() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllFavoriteMovies()).thenReturn(dataSourceFactory)
        repository.getAllFavoriteMovies()

        val movies = Resource.success(PageListUtil.mockPagedList(DataDummy.generateDummyMovie()))
        verify(local).getAllFavoriteMovies()
        assertNotNull(movies.data)
        assertEquals(moviesResponses.size.toLong(), movies.data?.size?.toLong())
    }

    @Test
    fun testGetAllFavoriteShow() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, ShowEntity>
        `when`(local.getAllFavoriteShows()).thenReturn(dataSourceFactory)
        repository.getAllFavoriteShows()

        val shows = Resource.success(PageListUtil.mockPagedList(DataDummy.generateDummyTvShow()))
        verify(local).getAllFavoriteShows()
        assertNotNull(shows.data)
        assertEquals(showsResponses.size.toLong(), shows.data?.size?.toLong())
    }

    @Test
    fun testSetFavoriteMovie() {
        val dataDummy = DataDummy.generateDummyMovie()[0]
        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        doNothing().`when`(local).insertMovieFavoriteById(dataDummy.id)
        repository.setFavoriteMovie(dataDummy.id)
        verify(local, times(1)).insertMovieFavoriteById(dataDummy.id)
    }

    @Test
    fun testSetFavoriteShow() {
        val dataDummy = DataDummy.generateDummyTvShow()[0]
        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        doNothing().`when`(local).insertShowFavoriteById(dataDummy.id)
        repository.setFavoriteShow(dataDummy.id)
        verify(local, times(1)).insertShowFavoriteById(dataDummy.id)
    }


    @Test
    fun testDeleteFavoriteMovie() {
        val dataDummy = DataDummy.generateDummyMovie()[0]
        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        doNothing().`when`(local).deleteFavoriteMovieById(dataDummy.id)
        repository.deleteFavoriteMovie(dataDummy.id)
        verify(local, times(1)).deleteFavoriteMovieById(dataDummy.id)
    }

    @Test
    fun testDeleteFavoriteShow() {
        val dataDummy = DataDummy.generateDummyTvShow()[0]
        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        doNothing().`when`(local).deleteFavoriteShowById(dataDummy.id)
        repository.deleteFavoriteShow(dataDummy.id)
        verify(local, times(1)).deleteFavoriteShowById(dataDummy.id)
    }

    @Test
    fun testSearchMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.searchAllMovies("")).thenReturn(dataSourceFactory)
        repository.searchAllMovies("")

        val movies = Resource.success(PageListUtil.mockPagedList(DataDummy.generateDummyMovie()))
        verify(local).searchAllMovies("")
        assertNotNull(movies.data)
        assertEquals(moviesResponses.size.toLong(), movies.data?.size?.toLong())
    }


    @Test
    fun testSearchShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, ShowEntity>
        val showName = DataDummy.generateDummyTvShow()[0].title
        `when`(local.searchAllShows(showName)).thenReturn(dataSourceFactory)
        repository.searchAllShows(showName)

        val shows = Resource.success(PageListUtil.mockPagedList(DataDummy.generateDummyTvShow()))
        verify(local).searchAllShows(showName)
        assertNotNull(shows.data)
        assertEquals(moviesResponses.size.toLong(), shows.data?.size?.toLong())
    }
}