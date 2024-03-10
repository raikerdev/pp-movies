package com.raikerdev.petproject.movies

import android.app.Application
import androidx.room.Room
import com.raikerdev.petproject.movies.data.database.MovieDatabase

class App: Application() {

    lateinit var db: MovieDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            MovieDatabase::class.java,
            "movie-db"
        ).build()

    }

}
