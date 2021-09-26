package com.example.filmjetpacksub2.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.filmjetpacksub2.data.source.local.LocalDataSource
import com.example.filmjetpacksub2.data.source.local.entity.MovieEntity
import com.example.filmjetpacksub2.data.source.local.entity.ShowEntity
import com.example.filmjetpacksub2.data.source.remote.ApiResponse
import com.example.filmjetpacksub2.data.source.remote.RemoteDataSource
import com.example.filmjetpacksub2.data.source.remote.response.MovieDetailsResponse
import com.example.filmjetpacksub2.data.source.remote.response.MovieObject
import com.example.filmjetpacksub2.data.source.remote.response.ShowsDetailsResponse
import com.example.filmjetpacksub2.data.source.remote.response.ShowsObject
import com.example.filmjetpacksub2.utils.AppExecutors
import com.example.filmjetpacksub2.utils.IdlingResource
import com.example.filmjetpacksub2.utils.MappingHelper
import com.example.filmjetpacksub2.value_object.Resource

class Repository private constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource,
        private val appExecutors: AppExecutors
) : IDataSource {

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
                remoteData: RemoteDataSource,
                localData: LocalDataSource,
                appExecutors: AppExecutors
        ): Repository =
                instance ?: synchronized(this) {
                    instance ?: Repository(remoteData, localData, appExecutors).apply {
                        instance = this
                    }
                }
    }

    override fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
                NetworkBoundResource<PagedList<MovieEntity>, ArrayList<MovieObject>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                    data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<ArrayList<MovieObject>>> =
                    remoteDataSource.getAllMovies()

            override fun saveCallResult(data: ArrayList<MovieObject>) {
                val filmList = ArrayList<MovieEntity>()
                for (response in data) {
                    val film = MovieEntity(
                            id = response.id,
                            releaseDate = response.releaseDate,
                            title = response.title,
                            posterPath = response.posterPath,
                            runTime = null,
                            overview = null,
                            genres = null,
                            isFavorite = 0
                    )
                    filmList.add(film)
                }

                localDataSource.insertMovies(filmList)
            }

        }.asLiveData()
    }

    override fun getAllShows(): LiveData<Resource<PagedList<ShowEntity>>> {
        return object :
                NetworkBoundResource<PagedList<ShowEntity>, ArrayList<ShowsObject>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<ShowEntity>> {
                val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build()
                return LivePagedListBuilder(localDataSource.getAllShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<ShowEntity>?): Boolean =
                    data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<ArrayList<ShowsObject>>> =
                    remoteDataSource.getAllShows()

            override fun saveCallResult(data: ArrayList<ShowsObject>) {
                val filmList = ArrayList<ShowEntity>()
                for (response in data) {
                    val film = ShowEntity(
                            id = response.id,
                            firstAirDate = response.firstAirDate,
                            title = response.title,
                            posterPath = response.posterPath,
                            runTime = null,
                            overview = null,
                            genres = null,
                            numOfEps = 0,
                            numOfSeasons = 0,
                            isFavorite = 0
                    )
                    filmList.add(film)
                }

                localDataSource.insertShows(filmList)
            }

        }.asLiveData()
    }

    override fun getSelectedMovie(movieId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieDetailsResponse>(appExecutors) {
            public override fun loadFromDB(): LiveData<MovieEntity> =
                    localDataSource.getSelectedMovieById(movieId)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                    data == null || data.equals(null)

            override fun createCall(): LiveData<ApiResponse<MovieDetailsResponse>> =
                    remoteDataSource.getSelectedMovie(movieId)

            override fun saveCallResult(data: MovieDetailsResponse) {
                val genre = MappingHelper.mapGenres(data.genres)
                val film = MovieEntity(
                       id = data.id,
                        releaseDate = data.releaseDate,
                        title = data.title,
                        posterPath = data.posterPath,
                        runTime = data.runTime,
                        overview = data.overview,
                        genres = genre,
                        isFavorite = 0
                )
                localDataSource.updateSelectedMovie(film)
            }

        }.asLiveData()

    }

    override fun getSelectedTvShow(showId: Int): LiveData<Resource<ShowEntity>> {
        return object : NetworkBoundResource<ShowEntity, ShowsDetailsResponse>(appExecutors) {
            public override fun loadFromDB(): LiveData<ShowEntity> =
                    localDataSource.getSelectedShowById(showId)

            override fun shouldFetch(data: ShowEntity?): Boolean =
                    data == null || data.equals(null)

            override fun createCall(): LiveData<ApiResponse<ShowsDetailsResponse>> =
                    remoteDataSource.getSelectedShow(showId)

            override fun saveCallResult(data: ShowsDetailsResponse) {
                val genre = MappingHelper.mapGenres(data.genres)
                val film = ShowEntity(
                        id = data.id,
                        firstAirDate = data.firstAirDate,
                        title = data.title,
                        posterPath = data.posterPath,
                        runTime = data.runTime[0],
                        overview = data.overview,
                        genres = genre,
                        numOfEps = data.numOfEps,
                        numOfSeasons = data.numOfSeasons,
                        isFavorite = 0
                )
                localDataSource.updateSelectedShow(film)
            }

        }.asLiveData()
    }

    override fun getAllFavoriteShows(): LiveData<PagedList<ShowEntity>> {
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(4)
            setPageSize(4)
        }.build()
        return LivePagedListBuilder(localDataSource.getAllFavoriteShows(), config).build()
    }

    override fun getAllFavoriteMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(4)
            setPageSize(4)
        }.build()
        return LivePagedListBuilder(localDataSource.getAllFavoriteMovies(), config).build()
    }

    override fun setFavoriteMovie(movieId: Int) {
        IdlingResource.increment()
        appExecutors.diskIO().execute { localDataSource.insertMovieFavoriteById(movieId) }
        IdlingResource.decrement()
    }

    override fun setFavoriteShow(showId: Int) {
        IdlingResource.increment()
        appExecutors.diskIO().execute { localDataSource.insertShowFavoriteById(showId) }
        IdlingResource.decrement()
    }

    override fun deleteFavoriteMovie(filmId: Int) {
        IdlingResource.increment()
        appExecutors.diskIO().execute { localDataSource.deleteFavoriteMovieById(filmId) }
        IdlingResource.decrement()
    }

    override fun deleteFavoriteShow(filmId: Int) {
        IdlingResource.increment()
        appExecutors.diskIO().execute { localDataSource.deleteFavoriteShowById(filmId) }
        IdlingResource.decrement()
    }

    override fun searchAllMovies(name:String): LiveData<PagedList<MovieEntity>>{
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(4)
            setPageSize(4)
        }.build()
        return LivePagedListBuilder(localDataSource.searchAllMovies(name), config).build()
    }

    override fun searchAllShows(name:String): LiveData<PagedList<ShowEntity>>{
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(4)
            setPageSize(4)
        }.build()
        return LivePagedListBuilder(localDataSource.searchAllShows(name), config).build()
    }
}
