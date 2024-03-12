package com.raikerdev.petproject.movies.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.raikerdev.petproject.movies.R
import com.raikerdev.petproject.movies.databinding.FragmentDetailBinding
import com.raikerdev.petproject.movies.ui.common.app
import com.raikerdev.petproject.movies.ui.common.launchAndCollect

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val safeArgs: DetailFragmentArgs by navArgs()
    private lateinit var component: DetailFragmentComponent
    private val viewModel: DetailViewModel by viewModels { component.detailViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = app.component.plus(DetailFragmentModule(safeArgs.movieId))
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
