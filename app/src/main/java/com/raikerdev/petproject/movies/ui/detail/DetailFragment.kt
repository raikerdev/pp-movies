package com.raikerdev.petproject.movies.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.raikerdev.petproject.movies.R
import com.raikerdev.petproject.movies.databinding.FragmentDetailBinding
import com.raikerdev.petproject.movies.data.MoviesRepository
import com.raikerdev.petproject.movies.data.RegionRepository
import com.raikerdev.petproject.movies.framework.AndroidPermissionChecker
import com.raikerdev.petproject.movies.framework.database.MovieRoomDataSource
import com.raikerdev.petproject.movies.framework.server.MovieServerDataSource
import com.raikerdev.petproject.movies.framework.PlayServicesLocationDataSource
import com.raikerdev.petproject.movies.usecases.FindMovieUseCase
import com.raikerdev.petproject.movies.usecases.SwitchMovieFavoriteUseCase
import com.raikerdev.petproject.movies.ui.common.app
import com.raikerdev.petproject.movies.ui.common.launchAndCollect

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val safeArgs: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModels {
        val application = requireActivity().app
        val regionRepository = RegionRepository(
            PlayServicesLocationDataSource(application),
            AndroidPermissionChecker(application)
        )
        val localDataSource = MovieRoomDataSource(application.db.movieDao())
        val remoteDataSource = MovieServerDataSource(getString(R.string.api_key))
        val repository = MoviesRepository(regionRepository, localDataSource, remoteDataSource)
        DetailViewModelFactory(
            safeArgs.movieId,
            FindMovieUseCase(repository),
            SwitchMovieFavoriteUseCase(repository)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)

        binding.movieDetailToolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        binding.movieDetailFavorite.setOnClickListener { viewModel.onFavoriteClicked() }

        viewLifecycleOwner.launchAndCollect(viewModel.state) {
            binding.movie = it.movie
        }
    }

}
