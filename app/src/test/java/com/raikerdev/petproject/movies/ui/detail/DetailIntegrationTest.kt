package com.raikerdev.petproject.movies.ui.detail

import app.cash.turbine.test
import com.raikerdev.petproject.movies.CoroutinesTestRule
import com.raikerdev.petproject.movies.data.database.DbMovie
import com.raikerdev.petproject.movies.data.server.RemoteMovie
import com.raikerdev.petproject.apptestshared.buildDatabaseMovies
import com.raikerdev.petproject.apptestshared.buildRepositoryWith
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
            localData = buildDatabaseMovies(1, 2, 3)
        )

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(2, awaitItem().movie!!.id)
            cancel()
        }
    }

    @Test
    fun `Favorite is updated in local data source`() = runTest{
        val vm = buildViewModelWith(
            movieId = 2,
            localData = buildDatabaseMovies(1, 2, 3)
        )
        vm.onFavoriteClicked()

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(false, awaitItem().movie!!.favorite)
            Assert.assertEquals(true, awaitItem().movie!!.favorite)
        }
    }

    private fun buildViewModelWith(
        movieId: Int,
        localData: List<DbMovie> = emptyList(),
        remoteData: List<RemoteMovie> = emptyList()
    ): DetailViewModel {
        val movieRepository = buildRepositoryWith(localData, remoteData)

        val findMovieUseCase = FindMovieUseCase(movieRepository)
        val switchMovieFavoriteUseCase = SwitchMovieFavoriteUseCase(movieRepository)
        return DetailViewModel(movieId, findMovieUseCase, switchMovieFavoriteUseCase)
    }

}
