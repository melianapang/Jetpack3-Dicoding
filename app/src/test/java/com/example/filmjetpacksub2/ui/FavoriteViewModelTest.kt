package com.example.filmjetpacksub2.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.filmjetpacksub2.data.Repository
import com.example.filmjetpacksub2.data.source.local.entity.MovieEntity
import com.example.filmjetpacksub2.data.source.local.entity.ShowEntity
import com.example.filmjetpacksub2.viewmodel.FavoriteViewModel
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {
    private lateinit var viewModel: FavoriteViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observerMovie: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var observerShow: Observer<PagedList<ShowEntity>>

    @Mock
    private lateinit var pagedListMovie: PagedList<MovieEntity>

    @Mock
    private lateinit var pagedListShow: PagedList<ShowEntity>

    @Before
    fun setUp() {
        viewModel = FavoriteViewModel(repository)
    }

    @Test
    fun testGetFavoriteFilm() {
        val dummyMovie = pagedListMovie
        `when`(dummyMovie.size).thenReturn(6)
        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = dummyMovie

        `when`(repository.getAllFavoriteMovies()).thenReturn(movies)
        val listMovies = viewModel.getFavoriteFilm().value
        verify(repository).getAllFavoriteMovies()
        assertNotNull(listMovies)
        assertEquals(6, listMovies?.size)

        viewModel.getFavoriteFilm().observeForever(observerMovie)
        verify(observerMovie).onChanged(dummyMovie)
    }

    @Test
    fun testGetFavoriteTvShows() {
        val dummyShow = pagedListShow
        `when`(dummyShow.size).thenReturn(6)
        val shows = MutableLiveData<PagedList<ShowEntity>>()
        shows.value = dummyShow

        `when`(repository.getAllFavoriteShows()).thenReturn(shows)
        val listShows = viewModel.getFavoriteTvShows().value
        verify(repository).getAllFavoriteShows()
        assertNotNull(listShows)
        assertEquals(6, listShows?.size)

        viewModel.getFavoriteTvShows().observeForever(observerShow)
        verify(observerShow).onChanged(dummyShow)
    }
}