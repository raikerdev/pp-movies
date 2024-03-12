package com.raikerdev.petproject.movies.di

import com.raikerdev.petproject.movies.ui.detail.DetailViewModelFactory
import com.raikerdev.petproject.movies.ui.main.MainViewModelFactory
import com.raikerdev.petproject.usecases.FindMovieUseCase
import com.raikerdev.petproject.usecases.GetPopularMoviesUseCase
import com.raikerdev.petproject.usecases.RequestPopularMoviesUseCase
import com.raikerdev.petproject.usecases.SwitchMovieFavoriteUseCase
import dagger.Module
import dagger.Provides


@Module
object ViewModelsModule {

    @Provides
    fun provideMainViewModelFactory(
        getPopularMoviesUseCase: GetPopularMoviesUseCase,
        requestPopularMoviesUseCase: RequestPopularMoviesUseCase
    ) = MainViewModelFactory(getPopularMoviesUseCase, requestPopularMoviesUseCase)

    @Provides
    fun provideDetailViewModelFactory(
        findMovieUseCase: FindMovieUseCase,
        switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase
    ) = DetailViewModelFactory(-1, findMovieUseCase, switchMovieFavoriteUseCase)

}
