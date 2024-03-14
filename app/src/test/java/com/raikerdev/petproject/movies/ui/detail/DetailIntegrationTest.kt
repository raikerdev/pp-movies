package com.raikerdev.petproject.movies.ui.detail

import app.cash.turbine.test
import com.raikerdev.petproject.data.MoviesRepository
import com.raikerdev.petproject.data.RegionRepository
import com.raikerdev.petproject.domain.Movie
import com.raikerdev.petproject.domain.test.sampleMovie
import com.raikerdev.petproject.movies.CoroutinesTestRule
import com.raikerdev.petproject.movies.ui.FakeLocalDataSource
import com.raikerdev.petproject.movies.ui.FakeLocationDataSource
import com.raikerdev.petproject.movies.ui.FakePermissionChecker
import com.raikerdev.petproject.movies.ui.FakeRemoteDataSource
import com.raikerdev.petproject.movies.ui.detail.DetailViewModel.UiState
import com.raikerdev.petproject.usecases.FindMovieUseCase
import com.raikerdev.petproject.usecases.SwitchMovieFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        val vm = buildViewModelWith(
            movieId = 2,
            localData = listOf(sampleMovie.copy(1), sampleMovie.copy(2))
        )

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(UiState(movie = sampleMovie.copy(2)), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Favorite is updated in local data source`() = runTest{
        val vm = buildViewModelWith(
            movieId = 2,
            localData = listOf(sampleMovie.copy(id = 1), sampleMovie.copy(id = 2))
        )
        vm.onFavoriteClicked()

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(UiState(movie = sampleMovie.copy(id = 2, favorite = false)), awaitItem())
            Assert.assertEquals(UiState(movie = sampleMovie.copy(id = 2, favorite = true)), awaitItem())
        }
    }

    private fun buildViewModelWith(
        movieId: Int,
        localData: List<Movie> = emptyList(),
        remoteData: List<Movie> = emptyList()
    ): DetailViewModel {
        val locationDataSource = FakeLocationDataSource()
        val permissionChecker = FakePermissionChecker()
        val regionRepository = RegionRepository(locationDataSource, permissionChecker)

        val localDataSource = FakeLocalDataSource().apply { movies.value = localData }
        val remoteDataSource = FakeRemoteDataSource().apply { movies = remoteData }
        val movieRepository = MoviesRepository(regionRepository, localDataSource, remoteDataSource)

        val findMovieUseCase = FindMovieUseCase(movieRepository)
        val switchMovieFavoriteUseCase = SwitchMovieFavoriteUseCase(movieRepository)
        return DetailViewModel(movieId, findMovieUseCase, switchMovieFavoriteUseCase)
    }

}
