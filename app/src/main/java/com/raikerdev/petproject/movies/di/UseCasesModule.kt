package com.raikerdev.petproject.movies.di

import com.raikerdev.petproject.data.MoviesRepository
import com.raikerdev.petproject.usecases.FindMovieUseCase
import com.raikerdev.petproject.usecases.GetPopularMoviesUseCase
import com.raikerdev.petproject.usecases.RequestPopularMoviesUseCase
import com.raikerdev.petproject.usecases.SwitchMovieFavoriteUseCase
import dagger.Module
import dagger.Provides

@Module
object UseCasesModule {

    @Provides
    fun provideFindMovieUseCase(moviesRepository: MoviesRepository) =
        FindMovieUseCase(moviesRepository)

    @Provides
    fun provideGetPopularMoviesUseCase(moviesRepository: MoviesRepository) =
        GetPopularMoviesUseCase(moviesRepository)

    @Provides
    fun provideRequestPopularMoviesUseCase(moviesRepository: MoviesRepository) =
        RequestPopularMoviesUseCase(moviesRepository)

    @Provides
    fun provideSwitchMovieFavoriteUseCase(moviesRepository: MoviesRepository) =
        SwitchMovieFavoriteUseCase(moviesRepository)

}
