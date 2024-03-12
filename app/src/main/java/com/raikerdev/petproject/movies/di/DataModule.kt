package com.raikerdev.petproject.movies.di

import com.raikerdev.petproject.data.MoviesRepository
import com.raikerdev.petproject.data.PermissionChecker
import com.raikerdev.petproject.data.RegionRepository
import com.raikerdev.petproject.data.datasource.LocationDataSource
import com.raikerdev.petproject.data.datasource.MovieLocalDataSource
import com.raikerdev.petproject.data.datasource.MovieRemoteDataSource
import dagger.Module
import dagger.Provides

@Module
object DataModule {

    @Provides
    fun provideRegionRepository(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = RegionRepository(locationDataSource, permissionChecker)

    @Provides
    fun provideMoviesRepository(
        regionRepository: RegionRepository,
        localDataSource: MovieLocalDataSource,
        remoteDataSource: MovieRemoteDataSource
    ) = MoviesRepository(regionRepository, localDataSource, remoteDataSource)

}
