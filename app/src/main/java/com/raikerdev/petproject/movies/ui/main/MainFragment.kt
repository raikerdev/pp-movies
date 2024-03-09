package com.raikerdev.petproject.movies.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raikerdev.petproject.movies.R
import com.raikerdev.petproject.movies.databinding.FragmentMainBinding
import com.raikerdev.petproject.movies.model.MoviesRepository
import com.raikerdev.petproject.movies.ui.common.app
import com.raikerdev.petproject.movies.ui.common.launchAndCollect

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(MoviesRepository(requireActivity().app))
    }

    private val adapter = MoviesAdapter { mainState.onMovieClicked(it) }
    private lateinit var mainState: MainState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainState = buildMainState()

        val binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }

        viewLifecycleOwner.launchAndCollect(viewModel.state) {
            binding.loading = it.loading
            binding.movies = it.movies
        }

        mainState.requestLocationPermission { viewModel.onUiReady() }

    }

}
