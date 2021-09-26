package com.example.filmjetpacksub2.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.filmjetpacksub2.data.source.remote.api.ApiClient
import com.example.filmjetpacksub2.data.source.remote.response.*
import com.example.filmjetpacksub2.utils.IdlingResource
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    companion object {
        private const val TAG = "RemoteDataSource"
//        private const val API_KEY = BuildConfig.API_KEY
        private const val API_KEY = "af28284beab32ee6b8b90560faf0201f"

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource().apply { instance = this }
            }
    }

    fun getAllMovies(): LiveData<ApiResponse<ArrayList<MovieObject>>> {
        IdlingResource.increment()
        val resultMovies = MutableLiveData<ApiResponse<ArrayList<MovieObject>>>()
        ApiClient.api.getAllMoviesFromAPI(API_KEY)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        val films = response.body()!!.results
                        resultMovies.value = ApiResponse.success(films)
                         IdlingResource.decrement()
                    } else {
                        val error = response.errorBody()!!.toString()
                        val message = StringBuilder()
                        error.let {
                            try {
                                message.append(JSONObject(it).getString("message"))
                            } catch (e: JSONException) {
                            }
                            message.append("\n")
                        }
                        message.append("Error code : ${response.code()}")
                        Log.e(TAG, "onFailure: $message")
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        return resultMovies
    }

    fun getAllShows() : LiveData<ApiResponse<ArrayList<ShowsObject>>> {
        IdlingResource.increment()
        val resultShows = MutableLiveData<ApiResponse<ArrayList<ShowsObject>>>()
        ApiClient.api.getAllShowsFromAPI(API_KEY)
            .enqueue(object : Callback<ShowsResponse> {
                override fun onResponse(
                    call: Call<ShowsResponse>,
                    response: Response<ShowsResponse>
                ) {
                    if (response.isSuccessful) {
                        val films = response.body()!!.results
                        resultShows.value = ApiResponse.success(films)
                        IdlingResource.decrement()
                    } else {
                        val error = response.errorBody()!!.toString()
                        val message = StringBuilder()
                        error.let {
                            try {
                                message.append(JSONObject(it).getString("message"))
                            } catch (e: JSONException) {
                            }
                            message.append("\n")
                        }
                        message.append("Error code : ${response.code()}")
                        Log.e(TAG, "onFailure: $message")
                    }
                }

                override fun onFailure(call: Call<ShowsResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        return resultShows
    }

    fun getSelectedMovie(id: Int) : LiveData<ApiResponse<MovieDetailsResponse>> {
        IdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<MovieDetailsResponse>>()
        ApiClient.api.getDetailMovieFromAPI(id, API_KEY)
            .enqueue(object : Callback<MovieDetailsResponse> {
                override fun onResponse(
                    call: Call<MovieDetailsResponse>,
                    response: Response<MovieDetailsResponse>
                ) {
                    if (response.isSuccessful) {
                        resultMovie.value = ApiResponse.success(response.body()!!)
                        IdlingResource.decrement()
                    } else {
                        val error = response.errorBody()!!.toString()
                        val message = StringBuilder()
                        error.let {
                            try {
                                message.append(JSONObject(it).getString("message"))
                            } catch (e: JSONException) {
                            }
                            message.append("\n")
                        }
                        message.append("Error code : ${response.code()}")
                        Log.e(TAG, "onFailure: $message")
                    }
                }

                override fun onFailure(call: Call<MovieDetailsResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        return resultMovie
    }

    fun getSelectedShow(id: Int) : LiveData<ApiResponse<ShowsDetailsResponse>> {
        IdlingResource.increment()
        val resultShow = MutableLiveData<ApiResponse<ShowsDetailsResponse>>()

        ApiClient.api.getDetailShowFromAPI(id, API_KEY)
            .enqueue(object : Callback<ShowsDetailsResponse> {
                override fun onResponse(
                    call: Call<ShowsDetailsResponse>,
                    response: Response<ShowsDetailsResponse>
                ) {
                    if (response.isSuccessful) {
                        resultShow.value = ApiResponse.success((response.body()!!))
                        IdlingResource.decrement()
                    } else {
                        val error = response.errorBody()!!.toString()
                        val message = StringBuilder()
                        error.let {
                            try {
                                message.append(JSONObject(it).getString("message"))
                            } catch (e: JSONException) {
                            }
                            message.append("\n")
                        }
                        message.append("Error code : ${response.code()}")
                        Log.e(TAG, "onFailure: $message")
                    }
                }

                override fun onFailure(call: Call<ShowsDetailsResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        return resultShow
    }
}