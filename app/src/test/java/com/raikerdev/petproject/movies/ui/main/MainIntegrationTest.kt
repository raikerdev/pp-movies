package com.raikerdev.petproject.movies.ui.main

import app.cash.turbine.test
import com.raikerdev.petproject.movies.CoroutinesTestRule
import com.raikerdev.petproject.movies.data.database.DbMovie
import com.raikerdev.petproject.movies.data.server.RemoteMovie
import com.raikerdev.petproject.movies.ui.buildDatabaseMovies
import com.raikerdev.petproject.movies.ui.buildRemoteMovies
import com.raikerdev.petproject.movies.ui.buildRepositoryWith
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
        val remoteData = buildRemoteMovies(4, 5, 6)
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

            val movies = awaitItem().movies!!
            Assert.assertEquals("Title 4", movies[0].title)
            Assert.assertEquals("Title 5", movies[1].title)
            Assert.assertEquals("Title 6", movies[2].title)

            cancel()
        }
    }

    @Test
    fun `Data is loaded from local source when available`() = runTest {
        val localData = buildDatabaseMovies(1, 2, 3)
        val remoteData = buildRemoteMovies(4, 5, 6)
        val vm = buildViewModelWith(
            localData = localData,
            remoteData = remoteData
        )

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())

            val movies = awaitItem().movies!!
            Assert.assertEquals("Title 1", movies[0].title)
            Assert.assertEquals("Title 2", movies[1].title)
            Assert.assertEquals("Title 3", movies[2].title)

            cancel()
        }

    }

    private fun buildViewModelWith(
        localData: List<DbMovie> = emptyList(),
        remoteData: List<RemoteMovie> = emptyList()
    ): MainViewModel {
        val movieRepository = buildRepositoryWith(localData, remoteData)

        val getPopularMoviesUseCase = GetPopularMoviesUseCase(movieRepository)
        val requestPopularMoviesUseCase = RequestPopularMoviesUseCase(movieRepository)
        return MainViewModel(getPopularMoviesUseCase, requestPopularMoviesUseCase)
    }

}
