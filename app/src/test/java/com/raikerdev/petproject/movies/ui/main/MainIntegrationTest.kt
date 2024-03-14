package com.raikerdev.petproject.movies.ui.main

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
import com.raikerdev.petproject.movies.ui.main.MainViewModel.UiState
import com.raikerdev.petproject.usecases.GetPopularMoviesUseCase
import com.raikerdev.petproject.usecases.RequestPopularMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `Data is loaded from server when local source is empty`() = runTest {
        val remoteData = listOf(sampleMovie.copy(1), sampleMovie.copy(2))
        val vm = buildViewModelWith(
            localData = emptyList(),
            remoteData = remoteData
        )

        vm.onUiReady()

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(UiState(movies = emptyList()), awaitItem())
            Assert.assertEquals(UiState(movies = emptyList(), loading = true), awaitItem())
            Assert.assertEquals(UiState(movies = emptyList(), loading = false), awaitItem())
            Assert.assertEquals(UiState(movies = remoteData, loading = false), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Data is loaded from local source when available`() = runTest {
        val localData = listOf(sampleMovie.copy(10), sampleMovie.copy(11))
        val remoteData = listOf(sampleMovie.copy(1), sampleMovie.copy(2))
        val vm = buildViewModelWith(
            localData = localData,
            remoteData = remoteData
        )

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(UiState(movies = localData), awaitItem())
            cancel()
        }

    }

    private fun buildViewModelWith(
        localData: List<Movie> = emptyList(),
        remoteData: List<Movie> = emptyList()
    ): MainViewModel {
        val locationDataSource = FakeLocationDataSource()
        val permissionChecker = FakePermissionChecker()
        val regionRepository = RegionRepository(locationDataSource, permissionChecker)

        val localDataSource = FakeLocalDataSource().apply { movies.value = localData }
        val remoteDataSource = FakeRemoteDataSource().apply { movies = remoteData }
        val movieRepository = MoviesRepository(regionRepository, localDataSource, remoteDataSource)

        val getPopularMoviesUseCase = GetPopularMoviesUseCase(movieRepository)
        val requestPopularMoviesUseCase = RequestPopularMoviesUseCase(movieRepository)
        return MainViewModel(getPopularMoviesUseCase, requestPopularMoviesUseCase)
    }

}
