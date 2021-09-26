package com.example.filmjetpacksub2.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.filmjetpacksub2.data.source.local.entity.MovieEntity
import com.example.filmjetpacksub2.data.source.local.entity.ShowEntity

@Database(entities = [MovieEntity::class, ShowEntity::class],
    version = 1,
    exportSchema = false)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao

    companion object {

        @Volatile
        private var INSTANCE: FilmDatabase? = null

        fun getInstance(context: Context): FilmDatabase {
            if (INSTANCE == null) {
                synchronized(FilmDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                                context.applicationContext,
                                FilmDatabase::class.java, "Films.db"
                        ).build()
                    }
                }
            }

            return INSTANCE as FilmDatabase
        }
    }
}