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
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        MovieDatabase::class.java,
        "movie-db"
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(db: MovieDatabase) = db.movieDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {
    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: MovieServerDataSource): MovieRemoteDataSource

    @Binds
    abstract fun bindLocalDataSource(localDataSource: MovieRoomDataSource): MovieLocalDataSource

    @Binds
    abstract fun bindLocationDataSource(locationDataSource: PlayServicesLocationDataSource): LocationDataSource

    @Binds
    abstract fun bindPermissionChecker(permissionChecker: AndroidPermissionChecker): PermissionChecker
}
