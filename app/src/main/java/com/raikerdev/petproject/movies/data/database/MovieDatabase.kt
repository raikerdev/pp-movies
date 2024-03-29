package com.raikerdev.petproject.movies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DbMovie::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

}
