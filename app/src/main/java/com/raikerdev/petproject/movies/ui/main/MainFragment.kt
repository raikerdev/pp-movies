package com.raikerdev.petproject.movies.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raikerdev.petproject.movies.R
import com.raikerdev.petproject.movies.databinding.FragmentMainBinding
import com.raikerdev.petproject.movies.data.MoviesRepository
import com.raikerdev.petproject.movies.data.RegionRepository
import com.raikerdev.petproject.movies.framework.AndroidPermissionChecker
import com.raikerdev.petproject.movies.framework.database.MovieRoomDataSource
import com.raikerdev.petproject.movies.framework.server.MovieServerDataSource
import com.raikerdev.petproject.movies.framework.PlayServicesLocationDataSource
import com.raikerdev.petproject.movies.usecases.GetPopularMoviesUseCase
import com.raikerdev.petproject.movies.usecases.RequestPopularMoviesUseCase
import com.raikerdev.petproject.movies.ui.common.app
import com.raikerdev.petproject.movies.ui.common.launchAndCollect

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        val application = requireActivity().app
        val regionRepository = RegionRepository(
            PlayServicesLocationDataSource(application),
            AndroidPermissionChecker(application)
        )
        val localDataSource = MovieRoomDataSource(application.db.movieDao())
        val remoteDataSource = MovieServerDataSource(getString(R.string.api_key))
        val repository = MoviesRepository(regionRepository, localDataSource, remoteDataSource)
        MainViewModelFactory(
            GetPopularMoviesUseCase(repository),
            RequestPopularMoviesUseCase(repository)
        )
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
            binding.error = it.error?.let(mainState::errorToString)
        }

        mainState.requestLocationPermission { viewModel.onUiReady() }

    }



}
