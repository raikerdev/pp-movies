package com.raikerdev.petproject.movies.ui.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.raikerdev.petproject.movies.databinding.ActivityDetailBinding
import com.raikerdev.petproject.movies.ui.common.getParcelableExtraCompat
import com.raikerdev.petproject.movies.ui.common.loadUrl

class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    private val viewModel: DetailViewModel by viewModels { DetailViewModelFactory(requireNotNull(intent.getParcelableExtraCompat(MOVIE))) }
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.state.observe(this, ::updateUI)
    }

    private fun updateUI(state: DetailViewModel.UiState) = with(binding) {
        val movie = state.movie
        movieDetailToolbar.title = movie.title
        movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780${movie.backdropPath ?: movie.posterPath}")
        movieDetailSummary.text = movie.overview
        movieDetailInfo.setMovie(movie)
    }
}