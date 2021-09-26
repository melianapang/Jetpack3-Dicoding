package com.example.filmjetpacksub2.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.example.filmjetpacksub2.data.Repository
import com.example.filmjetpacksub2.data.source.local.entity.MovieEntity
import com.example.filmjetpacksub2.utils.DataDummy
import com.example.filmjetpacksub2.value_object.Resource
import com.example.filmjetpacksub2.viewmodel.DetailViewModel
import com.example.filmjetpacksub2.viewmodel.FavoriteViewModel
import com.example.filmjetpacksub2.viewmodel.HomeViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.text.SimpleDateFormat
import java.util.concurrent.Executors

@RunWith(MockitoJUnitRunner::class)
class TestCase {
    private lateinit var detViewModel: DetailViewModel
    private lateinit var homeViewModel: HomeViewModel
    private val dummyShowId = DataDummy.generateDummyTvShow()[0].id
    private val dummyMovieId = DataDummy.generateDummyMovie()[0].id

    @get:Rule
    var thrown: ExpectedException = ExpectedException.none()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observerMovie: Observer<Resource<PagedList<MovieEntity>>>

    @Before
    fun setUp() {
        detViewModel = DetailViewModel(repository)
        homeViewModel = HomeViewModel(repository)
    }

    @Test(expected = Exception::class)
    fun getAllMoviesEmpty() {
        val films = PagedTestDataSources.snapshot()
        val expected = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        expected.value = Resource.success(films)

        `when`(repository.getAllMovies()).thenReturn(expected)

        homeViewModel.getFilm().observeForever(observerMovie)
        verify(observerMovie).onChanged(expected.value)

        val actualValueDataSize = homeViewModel.getFilm().value?.data?.size
        assertTrue("size of data should be 0, actual is $actualValueDataSize", actualValueDataSize == 0)
    }

    @Test(expected = Exception::class)
    fun getAllMoviesError() {
        val expectedMessage = "Please try later"
        val expected = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        expected.value = Resource.error(expectedMessage, null)

        `when`(repository.getAllMovies()).thenReturn(expected)

        homeViewModel.getFilm().observeForever(observerMovie)
        verify(observerMovie).onChanged(expected.value)

        val actualMessage = homeViewModel.getFilm().value?.message
        assertEquals(expectedMessage, actualMessage)
    }

    @Test(expected = Exception::class)
    fun testFalseFullDateFormat() {
        detViewModel.setSelectedTvShow(dummyShowId)
        val dummyData = detViewModel.getSelectedTvShow().value
        val dateFormat = SimpleDateFormat("MM/dd/yyyy")
        val date = dateFormat.format(dummyData?.data?.firstAirDate)
        assertNotSame("The data are the same", dummyData?.data?.firstAirDate, date)
    }

    @Test(expected = Exception::class)
    fun nullAndThrowsShows() {
        val detailViewModel = mock(DetailViewModel::class.java)
        doThrow().`when`(detailViewModel).setSelectedMovie(dummyShowId)
        detailViewModel.setSelectedMovie(dummyShowId)
    }

    @Test(expected = Exception::class)
    fun nullAndThrowsMovies() {
        val detailViewModel = mock(DetailViewModel::class.java)
        doThrow().`when`(detailViewModel).setSelectedTvShow(dummyMovieId)
        detailViewModel.setSelectedTvShow(dummyMovieId)
    }

    class PagedTestDataSources private constructor(private val items: List<MovieEntity>) : PositionalDataSource<MovieEntity>() {
        companion object {
            fun snapshot(items: List<MovieEntity> = listOf()): PagedList<MovieEntity> {
                return PagedList.Builder(PagedTestDataSources(items), 10)
                        .setNotifyExecutor(Executors.newSingleThreadExecutor())
                        .setFetchExecutor(Executors.newSingleThreadExecutor())
                        .build()
            }
        }

        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<MovieEntity>) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MovieEntity>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }
}