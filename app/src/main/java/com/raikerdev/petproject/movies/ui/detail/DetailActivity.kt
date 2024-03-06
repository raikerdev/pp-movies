package com.raikerdev.petproject.movies.ui.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.raikerdev.petproject.movies.databinding.ActivityDetailBinding
import com.raikerdev.petproject.movies.model.Movie
import com.raikerdev.petproject.movies.ui.common.getParcelableExtraCompat
import com.raikerdev.petproject.movies.ui.common.loadUrl

class DetailActivity : AppCompatActivity(), DetailPresenter.View {

    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    private val presenter = DetailPresenter()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie: Movie = requireNotNull(intent.getParcelableExtraCompat(MOVIE))
        presenter.onCreate(this@DetailActivity, movie)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun updateUI(movie: Movie) = with(binding) {
        movieDetailToolbar.title = movie.title
        movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780${movie.backdropPath ?: movie.posterPath}")
        movieDetailSummary.text = movie.overview
        movieDetailInfo.setMovie(movie)
    }
}