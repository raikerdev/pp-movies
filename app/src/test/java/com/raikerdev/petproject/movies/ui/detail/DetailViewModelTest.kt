package com.raikerdev.petproject.movies.ui.detail

import app.cash.turbine.test
import com.raikerdev.petproject.domain.test.sampleMovie
import com.raikerdev.petproject.movies.CoroutinesTestRule
import com.raikerdev.petproject.movies.ui.detail.DetailViewModel.UiState
import com.raikerdev.petproject.usecases.FindMovieUseCase
import com.raikerdev.petproject.usecases.SwitchMovieFavoriteUseCase
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
class DetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var findMovieUseCase: FindMovieUseCase

    @Mock
    lateinit var switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase

    private lateinit var vm: DetailViewModel

    private val movie = sampleMovie.copy(id = 2)

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        vm = buildViewModel()
        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(UiState(movie = movie), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Favorite action calls the corresponding use case`() = runTest {
        vm = buildViewModel()
        vm.onFavoriteClicked()
        runCurrent()

        verify(switchMovieFavoriteUseCase).invoke(movie)
    }

    private fun buildViewModel(): DetailViewModel {
        whenever(findMovieUseCase(2)).thenReturn(flowOf(movie))
        return DetailViewModel(2, findMovieUseCase, switchMovieFavoriteUseCase)
    }

}
