package com.example.filmjetpacksub2.di

import android.content.Context
import com.example.filmjetpacksub2.data.Repository
import com.example.filmjetpacksub2.data.source.local.LocalDataSource
import com.example.filmjetpacksub2.data.source.local.room.FilmDatabase
import com.example.filmjetpacksub2.data.source.remote.RemoteDataSource
import com.example.filmjetpacksub2.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): Repository {
        val database = FilmDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.filmDao())
        val appExecutors = AppExecutors()

        return Repository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}