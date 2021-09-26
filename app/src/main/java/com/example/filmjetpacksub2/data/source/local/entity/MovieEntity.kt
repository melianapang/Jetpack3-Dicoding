package com.example.filmjetpacksub2.data.source.local.entity

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieEntity")
data class MovieEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    val id: Int = 0,

    @ColumnInfo(name = "release_date")
    val releaseDate: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "poster_path")
    val posterPath: String,

    @Nullable
    @ColumnInfo(name = "run_time")
    val runTime: Int?,

    @Nullable
    @ColumnInfo(name = "overview")
    val overview: String?,

    @Nullable
    @ColumnInfo(name = "genres")
    val genres: String?,

    @NonNull
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Int
)