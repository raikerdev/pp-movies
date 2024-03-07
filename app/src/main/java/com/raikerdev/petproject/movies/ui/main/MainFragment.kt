package com.raikerdev.petproject.movies.ui.main

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.raikerdev.petproject.movies.R
import com.raikerdev.petproject.movies.databinding.FragmentMainBinding
import com.raikerdev.petproject.movies.model.Movie
import com.raikerdev.petproject.movies.model.MoviesRepository
import com.raikerdev.petproject.movies.ui.common.PermissionRequester
import com.raikerdev.petproject.movies.ui.common.launchAndCollect
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(MoviesRepository(requireActivity().application))
    }

    private val adapter = MoviesAdapter { viewModel.onMovieClicked(it) }

    private val coarsePermissionRequester = PermissionRequester(this, Manifest.permission.ACCESS_COARSE_LOCATION)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }

        viewLifecycleOwner.launchAndCollect(viewModel.state) { binding.updateUI(it) }
    }

    private fun FragmentMainBinding.updateUI(state: MainViewModel.UiState) {
        progress.isVisible = state.loading
        state.movies?.let(adapter::submitList)
        state.navigateTo?.let(::navigateTo)
        if(state.requestLocationPermission) {
            requestLocationPermission()
        }
    }

    private fun navigateTo(movie: Movie) {
        val navAction = MainFragmentDirections.actionMainToDetail(movie)
        findNavController().navigate(navAction)
        viewModel.onNavigationDone()
    }

    private fun requestLocationPermission() {
        viewLifecycleOwner.lifecycleScope.launch {
            coarsePermissionRequester.request()
            viewModel.onLocationPermissionChecked()
        }
    }

}