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
import com.raikerdev.petproject.movies.data.server.RemoteService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
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

    @Provides
    @Singleton
    fun provideRemoteService(): RemoteService {
        val okHttpClient = HttpLoggingInterceptor().run {
            level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder().addInterceptor(this).build()
        }

        val builder = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return builder.create()
    }
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
