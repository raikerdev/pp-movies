package com.raikerdev.petproject.movies.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raikerdev.petproject.movies.R
import com.raikerdev.petproject.movies.databinding.FragmentMainBinding
import com.raikerdev.petproject.movies.model.MoviesRepository
import com.raikerdev.petproject.movies.ui.common.launchAndCollect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(MoviesRepository(requireActivity().application))
    }

    private val adapter = MoviesAdapter { mainState.onMovieClicked(it) }
    private lateinit var mainState: MainState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainState = buildMainState()

        val binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }

        with(viewModel.state) {
            diff({ it.movies }) { adapter.submitList(it) }
            diff({ it.loading }) { binding.progress.isVisible = it }
        }

        mainState.requestLocationPermission { viewModel.onUiReady() }

    }

    private fun <T, U> Flow<T>.diff(mapF: (T) -> U, body: (U) -> Unit) {
        viewLifecycleOwner.launchAndCollect(
            flow = map(mapF).distinctUntilChanged(),
            body = body
        )
    }

}