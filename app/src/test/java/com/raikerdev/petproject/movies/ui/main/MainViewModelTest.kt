package com.raikerdev.petproject.movies.ui.main

import app.cash.turbine.test
import com.raikerdev.petproject.domain.test.sampleMovie
import com.raikerdev.petproject.movies.CoroutinesTestRule
import com.raikerdev.petproject.movies.ui.main.MainViewModel.UiState
import com.raikerdev.petproject.usecases.GetPopularMoviesUseCase
import com.raikerdev.petproject.usecases.RequestPopularMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase
    @Mock
    lateinit var requestPopularMoviesUseCase: RequestPopularMoviesUseCase

    private lateinit var vm: MainViewModel

    private val movies = listOf(sampleMovie.copy(1))

    @Test
    fun `State is updated with current cached content immediately`() = runTest {
        vm = buildViewModel()

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(UiState(movies = movies), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Progress is shown when screen start and hidden when it finishes requesting movies`() = runTest {
        vm = buildViewModel()
        vm.onUiReady()

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(UiState(movies = movies), awaitItem())
            Assert.assertEquals(UiState(movies = movies, loading = true), awaitItem())
            Assert.assertEquals(UiState(movies = movies, loading = false), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Popular movies are requested when UI screen starts`() = runTest {
        vm = buildViewModel()
        vm.onUiReady()
        runCurrent()

        verify(requestPopularMoviesUseCase).invoke()
    }

    private fun buildViewModel(): MainViewModel {
        whenever(getPopularMoviesUseCase()).thenReturn(flowOf(movies))
        return MainViewModel(getPopularMoviesUseCase, requestPopularMoviesUseCase)
    }

}
