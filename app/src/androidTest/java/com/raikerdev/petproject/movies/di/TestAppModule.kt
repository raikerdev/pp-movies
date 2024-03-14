package com.raikerdev.petproject.movies.di

import android.app.Application
import androidx.room.Room
import com.raikerdev.petproject.apptestshared.FakeRemoteService
import com.raikerdev.petproject.apptestshared.buildRemoteMovies
import com.raikerdev.petproject.movies.R
import com.raikerdev.petproject.movies.data.database.MovieDao
import com.raikerdev.petproject.movies.data.database.MovieDatabase
import com.raikerdev.petproject.movies.data.server.RemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {
    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.inMemoryDatabaseBuilder(
        app,
        MovieDatabase::class.java
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(db: MovieDatabase): MovieDao = db.movieDao()

    @Provides
    @Singleton
    fun provideRemoteService(): RemoteService =
        FakeRemoteService(buildRemoteMovies(1, 2, 3, 4, 5, 6))
}
