package com.raikerdev.petproject.movies.ui.main

import com.raikerdev.petproject.usecases.GetPopularMoviesUseCase
import com.raikerdev.petproject.usecases.RequestPopularMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MainFragmentModule {
    @Provides
    fun provideMainViewModelFactory(
        getPopularMoviesUseCase: GetPopularMoviesUseCase,
        requestPopularMoviesUseCase: RequestPopularMoviesUseCase
    ) = MainViewModelFactory(getPopularMoviesUseCase, requestPopularMoviesUseCase)
}

@Subcomponent(modules = [MainFragmentModule::class])
interface MainFragmentComponent {
    val mainViewModelFactory: MainViewModelFactory
}
