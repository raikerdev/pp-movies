package com.raikerdev.petproject.movies.di

import com.raikerdev.petproject.apptestshared.FakeMovieDao
import com.raikerdev.petproject.apptestshared.FakeRemoteService
import com.raikerdev.petproject.apptestshared.buildRemoteMovies
import com.raikerdev.petproject.movies.data.database.MovieDao
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
    fun provideApiKey(): String = "1234"

    @Provides
    @Singleton
    fun provideMovieDao(): MovieDao = FakeMovieDao()

    @Provides
    @Singleton
    fun provideRemoteService(): RemoteService =
        FakeRemoteService(buildRemoteMovies(1, 2, 3, 4, 5, 6))
}
