package com.example.filmjetpacksub2.utils

import com.example.filmjetpacksub2.data.source.remote.response.Genres
import java.util.*

object MappingHelper {
    private const val POSTER_PATH_LINK = "https://image.tmdb.org/t/p/w500"

    fun mapGenres(genres : ArrayList<Genres>?): String{
        val genreStr = StringBuilder()
        val genreSize = genres?.size ?: 0
        for (i in 0 until genreSize - 1) {
            val genre = genres?.get(i)?.name
            genreStr.append(genre)
            if (i < genreSize - 2) genreStr.append(", ")
        }

        return genreStr.toString()
    }

    fun mapPosterPath(data:String?):String{
        val pathStr = StringBuilder()
        pathStr.append(POSTER_PATH_LINK).append(data)
        return pathStr.toString()
    }
}