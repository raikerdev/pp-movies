package com.raikerdev.petproject.movies.di

import android.app.Application
import androidx.room.Room
import com.raikerdev.petproject.data.PermissionChecker
import com.raikerdev.petproject.data.datasource.LocationDataSource
import com.raikerdev.petproject.data.datasource.MovieLocalDataSource
import com.raikerdev.petproject.data.datasource.MovieRemoteDataSource
import com.raikerdev.petproject.movies.R
import com.raikerdev.petproject.movies.data.AndroidPermissionChecker
import com.raikerdev.petproject.movies.data.PlayServicesLocationDataSource
import com.raikerdev.petproject.movies.data.database.MovieDatabase
import com.raikerdev.petproject.movies.data.database.MovieRoomDataSource
import com.raikerdev.petproject.movies.data.server.MovieServerDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun provideApiKey(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        MovieDatabase::class.java,
        "movie-db"
    ).build()

    @Provides
    fun provideRemoteDataSource(@Named("apiKey") apiKey: String): MovieRemoteDataSource =
        MovieServerDataSource(apiKey)

    @Provides
    fun provideLocalDataSource(db: MovieDatabase): MovieLocalDataSource =
        MovieRoomDataSource(db.movieDao())

    @Provides
    fun provideLocationDataSource(app: Application): LocationDataSource =
        PlayServicesLocationDataSource(app)

    @Provides
    fun providePermissionChecker(app: Application): PermissionChecker =
        AndroidPermissionChecker(app)

}
