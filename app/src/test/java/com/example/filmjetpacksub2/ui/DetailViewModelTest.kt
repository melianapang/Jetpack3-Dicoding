package com.example.filmjetpacksub2.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.filmjetpacksub2.data.Repository
import com.example.filmjetpacksub2.data.source.local.LocalDataSource
import com.example.filmjetpacksub2.data.source.local.entity.MovieEntity
import com.example.filmjetpacksub2.data.source.local.entity.ShowEntity
import com.example.filmjetpacksub2.utils.DataDummy
import com.example.filmjetpacksub2.value_object.Resource
import com.example.filmjetpacksub2.viewmodel.DetailViewModel
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private val dummyShowId = DataDummy.generateDummyTvShow()[0].id
    private val dummyMovieId = DataDummy.generateDummyMovie()[0].id
    private val local = mock(LocalDataSource::class.java)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observerMovie: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var observerShow: Observer<Resource<ShowEntity>>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(repository)
        viewModel.setSelectedMovie(dummyMovieId)
        viewModel.setSelectedTvShow(dummyShowId)
    }

    @Test
    fun testGetSelectedMovie() {
        val movieObject = Resource.success(DataDummy.generateDummyMovie()[0])
        val dummyMovie = MutableLiveData<Resource<MovieEntity>>()
        dummyMovie.value = movieObject

        `when`(repository.getSelectedMovie(dummyMovieId)).thenReturn(dummyMovie)
        val selectedMovie = viewModel.getSelectedMovie().value

        assertNotNull(selectedMovie)
        assertEquals(movieObject.data?.id, selectedMovie?.data?.id)
        assertEquals(movieObject.data?.title, selectedMovie?.data?.title)
        assertEquals(movieObject.data?.runTime, selectedMovie?.data?.runTime)
        assertEquals(movieObject.data?.overview, selectedMovie?.data?.overview)
        assertEquals(movieObject.data?.genres, selectedMovie?.data?.genres)
        assertEquals(movieObject.data?.releaseDate, selectedMovie?.data?.releaseDate)

        viewModel.getSelectedMovie().observeForever(observerMovie)
        verify(observerMovie).onChanged(movieObject)
    }

    @Test
    fun testGetSelectedTvShow() {
        val showObject = Resource.success(DataDummy.generateDummyTvShow()[0])
        val dummyShow = MutableLiveData<Resource<ShowEntity>>()
        dummyShow.value = showObject

        `when`(repository.getSelectedTvShow(dummyMovieId)).thenReturn(dummyShow)
        val selectedShow = viewModel.getSelectedTvShow().value

        assertNotNull(selectedShow)
        assertEquals(showObject.data?.id, selectedShow?.data?.id)
        assertEquals(showObject.data?.title, selectedShow?.data?.title)
        assertEquals(showObject.data?.runTime, selectedShow?.data?.runTime)
        assertEquals(showObject.data?.overview, selectedShow?.data?.overview)
        assertEquals(showObject.data?.genres, selectedShow?.data?.genres)
        viewModel.getSelectedTvShow().observeForever(observerShow)
        verify(observerShow).onChanged(showObject)
    }

    @Test
    fun testSetFavoriteMovie() {
        val dataDummy = DataDummy.generateDummyMovie()[0]
        doNothing().`when`(local).insertMovieFavoriteById(dataDummy.id)
        repository.setFavoriteMovie(dataDummy.id)

        verify(repository).setFavoriteMovie(dataDummy.id)
    }

    @Test
    fun testSetFavoriteShow() {
        val dataDummy = DataDummy.generateDummyTvShow()[0]
        doNothing().`when`(local).insertShowFavoriteById(dataDummy.id)
        repository.setFavoriteShow(dataDummy.id)

        verify(repository).setFavoriteShow(dataDummy.id)
    }

    @Test
    fun testDeleteFavoriteMovie() {
        val dataDummy = DataDummy.generateDummyMovie()[0]
        doNothing().`when`(local).deleteFavoriteMovieById(dataDummy.id)
        repository.deleteFavoriteMovie(dataDummy.id)

        verify(repository).deleteFavoriteMovie(dataDummy.id)
    }

    @Test
    fun testDeleteFavoriteShow() {
        val dataDummy = DataDummy.generateDummyTvShow()[0]
        doNothing().`when`(local).deleteFavoriteShowById(dataDummy.id)
        repository.deleteFavoriteShow(dataDummy.id)

        verify(repository).deleteFavoriteShow(dataDummy.id)
    }
}