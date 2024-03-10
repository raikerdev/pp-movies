package com.raikerdev.petproject.movies

import android.app.Application
import androidx.room.Room
import com.raikerdev.petproject.data.MoviesRepository
import com.raikerdev.petproject.data.PermissionChecker
import com.raikerdev.petproject.data.RegionRepository
import com.raikerdev.petproject.data.datasource.LocationDataSource
import com.raikerdev.petproject.data.datasource.MovieLocalDataSource
import com.raikerdev.petproject.data.datasource.MovieRemoteDataSource
import com.raikerdev.petproject.movies.data.AndroidPermissionChecker
import com.raikerdev.petproject.movies.data.PlayServicesLocationDataSource
import com.raikerdev.petproject.movies.data.database.MovieDatabase
import com.raikerdev.petproject.movies.data.database.MovieRoomDataSource
import com.raikerdev.petproject.movies.data.server.MovieServerDataSource
import com.raikerdev.petproject.movies.ui.detail.DetailViewModel
import com.raikerdev.petproject.movies.ui.main.MainViewModel
import com.raikerdev.petproject.usecases.FindMovieUseCase
import com.raikerdev.petproject.usecases.GetPopularMoviesUseCase
import com.raikerdev.petproject.usecases.RequestPopularMoviesUseCase
import com.raikerdev.petproject.usecases.SwitchMovieFavoriteUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDi() {
    startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@initDi)
        modules(appModule, dataModule, useCasesModule)
    }
}

private val appModule = module {
    single(named("apiKey")) { androidContext().getString(R.string.api_key) }
    single {
        Room.databaseBuilder(
            get(),
            MovieDatabase::class.java, "movie-db"
        ).build()
    }
    single { get<MovieDatabase>().movieDao() }

    factory<MovieLocalDataSource> { MovieRoomDataSource(get()) }
    factory<MovieRemoteDataSource> { MovieServerDataSource(get(named("apiKey"))) }
    factory<LocationDataSource> { PlayServicesLocationDataSource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }

    viewModel { MainViewModel(get(), get()) }
    viewModel { (id: Int) -> DetailViewModel(id, get(), get()) }
}

private val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { MoviesRepository(get(), get(), get()) }
}

private val useCasesModule = module {
    factory { GetPopularMoviesUseCase(get()) }
    factory { RequestPopularMoviesUseCase(get()) }
    factory { FindMovieUseCase(get()) }
    factory { SwitchMovieFavoriteUseCase(get()) }
}
