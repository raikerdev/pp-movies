package com.raikerdev.petproject.movies.ui.detail

import com.raikerdev.petproject.usecases.FindMovieUseCase
import com.raikerdev.petproject.usecases.SwitchMovieFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class DetailFragmentModule(private val movieId: Int) {
    @Provides
    fun provideDetailViewModelFactory(
        findMovieUseCase: FindMovieUseCase,
        switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase
    ) = DetailViewModelFactory(movieId, findMovieUseCase, switchMovieFavoriteUseCase)
}

@Subcomponent(modules = [DetailFragmentModule::class])
interface DetailFragmentComponent {
    val detailViewModelFactory: DetailViewModelFactory
}
