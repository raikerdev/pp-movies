package com.raikerdev.petproject.movies.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.raikerdev.petproject.movies.R
import com.raikerdev.petproject.movies.databinding.FragmentDetailBinding
import com.raikerdev.petproject.movies.ui.common.launchAndCollect
import com.raikerdev.petproject.movies.ui.common.loadUrl

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val safeArgs: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModels { DetailViewModelFactory(safeArgs.movie) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)

        binding.movieDetailToolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        viewLifecycleOwner.launchAndCollect(viewModel.state) { binding.updateUI(it) }
    }

    private fun FragmentDetailBinding.updateUI(state: DetailViewModel.UiState) {
        val movie = state.movie
        movieDetailToolbar.title = movie.title
        movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780${movie.backdropPath ?: movie.posterPath}")
        movieDetailSummary.text = movie.overview
        movieDetailInfo.setMovie(movie)
    }

}