package com.example.filmjetpacksub2.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.filmjetpacksub2.data.Repository
import com.example.filmjetpacksub2.data.source.local.entity.MovieEntity
import com.example.filmjetpacksub2.data.source.local.entity.ShowEntity
import com.example.filmjetpacksub2.utils.DataDummy
import com.example.filmjetpacksub2.value_object.Resource
import com.example.filmjetpacksub2.viewmodel.HomeViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    private lateinit var viewModel: HomeViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observerMovie: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var observerShow: Observer<Resource<PagedList<ShowEntity>>>

    @Mock
    private lateinit var observerMovieDB: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var observerShowDB: Observer<PagedList<ShowEntity>>

    @Mock
    private lateinit var pagedListMovie: PagedList<MovieEntity>

    @Mock
    private lateinit var pagedListShow: PagedList<ShowEntity>

    @Before
    fun setUp() {
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun testGetFilm() {
        val dummyMovie =Resource.success(pagedListMovie)
        `when`(dummyMovie.data?.size).thenReturn(6)
        val movies = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movies.value = dummyMovie

        `when`(repository.getAllMovies()).thenReturn(movies)
        val listMovies = viewModel.getFilm().value
        verify(repository).getAllMovies()
        assertNotNull(listMovies)
        assertEquals(6, listMovies?.data?.size)

        viewModel.getFilm().observeForever(observerMovie)
        verify(observerMovie).onChanged(dummyMovie)
    }

    @Test
    fun testGetTvShows() {
        val dummyShow = Resource.success(pagedListShow)
        `when`(dummyShow.data?.size).thenReturn(6)
        val shows = MutableLiveData<Resource<PagedList<ShowEntity>>>()
        shows.value = dummyShow

        `when`(repository.getAllShows()).thenReturn(shows)
        val listMovies = viewModel.getTvShows().value
        verify(repository).getAllShows()
        assertNotNull(listMovies)
        assertEquals(6, listMovies?.data?.size)

        viewModel.getTvShows().observeForever(observerShow)
        verify(observerShow).onChanged(dummyShow)
    }

    @Test
    fun testSearhcMovies() {
        val dummyMovie = pagedListMovie
        val movieName = DataDummy.generateDummyMovie()[0].title
        `when`(dummyMovie.size).thenReturn(6)
        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = dummyMovie

        `when`(repository.searchAllMovies(movieName)).thenReturn(movies)
        val listMovies = viewModel.searchFilm(movieName).value
        verify(repository).searchAllMovies(movieName)
        assertNotNull(listMovies)
        assertEquals(6, listMovies?.size)

        viewModel.searchFilm(movieName).observeForever(observerMovieDB)
        verify(observerMovieDB).onChanged(dummyMovie)
    }


    @Test
    fun testSearchShows() {
        val dummyShow = pagedListShow
        val showName = DataDummy.generateDummyTvShow()[0].title
        `when`(dummyShow.size).thenReturn(6)
        val shows = MutableLiveData<PagedList<ShowEntity>>()
        shows.value = dummyShow

        `when`(repository.searchAllShows(showName)).thenReturn(shows)
        val listMovies = viewModel.searchTvShows(showName).value
        verify(repository).searchAllShows(showName)
        assertNotNull(listMovies)
        assertEquals(6, listMovies?.size)

        viewModel.searchTvShows(showName).observeForever(observerShowDB)
        verify(observerShowDB).onChanged(dummyShow)
    }
}