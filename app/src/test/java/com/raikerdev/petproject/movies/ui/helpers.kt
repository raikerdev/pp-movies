package com.raikerdev.petproject.movies.ui

import com.raikerdev.petproject.data.MoviesRepository
import com.raikerdev.petproject.data.RegionRepository
import com.raikerdev.petproject.movies.data.database.DbMovie
import com.raikerdev.petproject.movies.data.database.MovieRoomDataSource
import com.raikerdev.petproject.movies.data.server.MovieServerDataSource
import com.raikerdev.petproject.movies.data.server.RemoteMovie

fun buildRepositoryWith(
    localData: List<DbMovie> = emptyList(),
    remoteData: List<RemoteMovie> = emptyList()
): MoviesRepository {
    val locationDataSource = FakeLocationDataSource()
    val permissionChecker = FakePermissionChecker()
    val regionRepository = RegionRepository(locationDataSource, permissionChecker)

    val localDataSource = MovieRoomDataSource(FakeMovieDao(localData))
    val remoteDataSource = MovieServerDataSource("1234", FakeRemoteService(remoteData))
    return MoviesRepository(regionRepository, localDataSource, remoteDataSource)
}

fun buildDatabaseMovies(vararg id: Int) = id.map {
    DbMovie(
        id = it,
        title = "Title $it",
        overview = "Overview $it",
        releaseDate = "01/01/2025",
        posterPath = "",
        backdropPath = "",
        originalLanguage = "EN",
        originalTitle = "Original Title $it",
        popularity = 5.0,
        voteAverage = 5.1,
        favorite = false
    )
}

fun buildRemoteMovies(vararg id: Int) = id.map {
    RemoteMovie(
        adult = false,
        backdropPath = "",
        genreIds = emptyList(),
        id = it,
        originalLanguage = "EN",
        originalTitle = "Original Title $it",
        overview = "Overview $it",
        popularity = 5.0,
        posterPath = "",
        releaseDate = "01/01/2025",
        title = "Title $it",
        video = false,
        voteAverage = 5.1,
        voteCount = 10
    )
}
