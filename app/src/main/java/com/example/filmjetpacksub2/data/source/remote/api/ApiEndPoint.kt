package com.example.filmjetpacksub2.data.source.remote.api

import com.example.filmjetpacksub2.data.source.remote.response.MovieDetailsResponse
import com.example.filmjetpacksub2.data.source.remote.response.MovieResponse
import com.example.filmjetpacksub2.data.source.remote.response.ShowsDetailsResponse
import com.example.filmjetpacksub2.data.source.remote.response.ShowsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndPoint {
    @GET("discover/movie")
    fun getAllMoviesFromAPI(@Query("api_key") apiKey: String?): Call<MovieResponse>

    @GET("discover/tv")
    fun getAllShowsFromAPI(@Query("api_key") apiKey: String?): Call<ShowsResponse>

    @GET("movie/{movie_id}")
    fun getDetailMovieFromAPI(
        @Path("movie_id") id: Int,
        @Query("api_key") key: String
    ): Call<MovieDetailsResponse>

    @GET("tv/{tv_id}")
    fun getDetailShowFromAPI(
        @Path("tv_id") id: Int,
        @Query("api_key") key: String
    ): Call<ShowsDetailsResponse>
}