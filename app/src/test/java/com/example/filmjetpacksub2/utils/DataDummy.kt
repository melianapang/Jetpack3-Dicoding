package com.example.filmjetpacksub2.utils

import android.annotation.SuppressLint
import com.example.filmjetpacksub2.R
import com.example.filmjetpacksub2.data.source.local.entity.MovieEntity
import com.example.filmjetpacksub2.data.source.local.entity.ShowEntity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object DataDummy {

    fun generateDummyMovie(): ArrayList<MovieEntity> {
        val films = ArrayList<MovieEntity>()

        films.add(
                MovieEntity(
                        1,
                        "02/14/2019",
                        "Alita: Battle Angel",
                        R.drawable.poster_alita.toString(),
                        50,
                        "When Alita awakens with no memory of who she is in a future world she does not recognize, she is taken in by Ido, a compassionate doctor who realizes that somewhere in this abandoned cyborg shell is the heart and soul of a young woman with an extraordinary past.",
                        "Action, Science Fiction, Adventure",
                        0
                )
        )
        films.add(
                MovieEntity(
                        2,
                        "11/2/2018",
                        "Bohemian Rhapsody",
                        R.drawable.poster_bohemian.toString(),
                        120,
                        "Singer Freddie Mercury, guitarist Brian May, drummer Roger Taylor and bass guitarist John Deacon take the music world by storm when they form the rock 'n' roll band Queen in 1970. Hit songs become instant classics. When Mercury's increasingly wild lifestyle starts to spiral out of control, Queen soon faces its greatest challenge yet – finding a way to keep the band together amid the success and excess.",
                        "Music, Drama, History",
                        0
                )
        )
        films.add(
                MovieEntity(
                        3,
                        "02/14/2019",
                        "Alita: Battle Angel",
                        R.drawable.poster_alita.toString(),
                        50,
                        "When Alita awakens with no memory of who she is in a future world she does not recognize, she is taken in by Ido, a compassionate doctor who realizes that somewhere in this abandoned cyborg shell is the heart and soul of a young woman with an extraordinary past.",
                        "Action, Science Fiction, Adventure",
                        0
                )
        )
        films.add(
                MovieEntity(
                        4,
                        "11/2/2018",
                        "Bohemian Rhapsody",
                        R.drawable.poster_bohemian.toString(),
                        120,
                        "Singer Freddie Mercury, guitarist Brian May, drummer Roger Taylor and bass guitarist John Deacon take the music world by storm when they form the rock 'n' roll band Queen in 1970. Hit songs become instant classics. When Mercury's increasingly wild lifestyle starts to spiral out of control, Queen soon faces its greatest challenge yet – finding a way to keep the band together amid the success and excess.",
                        "Music, Drama, History",
                        0
                )
        )
        films.add(
                MovieEntity(
                        5,
                        "02/14/2019",
                        "Alita: Battle Angel",
                        R.drawable.poster_alita.toString(),
                        50,
                        "When Alita awakens with no memory of who she is in a future world she does not recognize, she is taken in by Ido, a compassionate doctor who realizes that somewhere in this abandoned cyborg shell is the heart and soul of a young woman with an extraordinary past.",
                        "Action, Science Fiction, Adventure",
                        0
                )
        )
        films.add(
                MovieEntity(
                        6,
                        "11/2/2018",
                        "Bohemian Rhapsody",
                        R.drawable.poster_bohemian.toString(),
                        120,
                        "Singer Freddie Mercury, guitarist Brian May, drummer Roger Taylor and bass guitarist John Deacon take the music world by storm when they form the rock 'n' roll band Queen in 1970. Hit songs become instant classics. When Mercury's increasingly wild lifestyle starts to spiral out of control, Queen soon faces its greatest challenge yet – finding a way to keep the band together amid the success and excess.",
                        "Music, Drama, History",
                        0
                )
        )

        return films
    }

    fun generateDummyTvShow(): ArrayList<ShowEntity> {

        val shows = ArrayList<ShowEntity>()

        shows.add(
                ShowEntity(
                        1,
                        "2012",
                        "Arrow",
                        R.drawable.poster_arrow.toString(),
                        42,
                        "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.",
                        "Crime, Drama, Mystery, Action & Adventure",
                        19,
                        6,
                        0
                )
        )
        shows.add(
                ShowEntity(
                        2,
                        "8/18/2012",
                        "Fairy Tail the Movie: Phoenix Priestess",
                        R.drawable.poster_fairytail.toString(),
                        126,
                        "Mighty mages must fight to save the world when, in his quest for immortality, a mad prince foolishly unleashes an ancient and powerful force.",
                        "Action, Comedy, Drama, Adventure, Animation, Fantasy",
                        8,
                        1,
                        0
                )
        )
        shows.add(
                ShowEntity(
                        3,
                        "2012",
                        "Arrow",
                        R.drawable.poster_arrow.toString(),
                        42,
                        "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.",
                        "Crime, Drama, Mystery, Action & Adventure",
                        19,
                        6,
                        0
                )
        )
        shows.add(
                ShowEntity(
                        4,
                        "8/18/2012",
                        "Fairy Tail the Movie: Phoenix Priestess",
                        R.drawable.poster_fairytail.toString(),
                        126,
                        "Mighty mages must fight to save the world when, in his quest for immortality, a mad prince foolishly unleashes an ancient and powerful force.",
                        "Action, Comedy, Drama, Adventure, Animation, Fantasy",
                        8,
                        1,
                        0
                )
        )
        shows.add(
                ShowEntity(
                        5,
                        "2012",
                        "Arrow",
                        R.drawable.poster_arrow.toString(),
                        42,
                        "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.",
                        "Crime, Drama, Mystery, Action & Adventure",
                        19,
                        6,
                        0
                )
        )
        shows.add(
                ShowEntity(
                        6,
                        "8/18/2012",
                        "Fairy Tail the Movie: Phoenix Priestess",
                        R.drawable.poster_fairytail.toString(),
                        126,
                        "Mighty mages must fight to save the world when, in his quest for immortality, a mad prince foolishly unleashes an ancient and powerful force.",
                        "Action, Comedy, Drama, Adventure, Animation, Fantasy",
                        8,
                        1,
                0
                )
        )
        return shows
    }
}