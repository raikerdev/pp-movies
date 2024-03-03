package com.raikerdev.petproject.movies.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.raikerdev.petproject.movies.databinding.ActivityDetailBinding
import com.raikerdev.petproject.movies.model.Movie

class DetailActivity : AppCompatActivity() {
    companion object {
        const val MOVIE = "DetailActivity:movie"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityDetailBinding.inflate(layoutInflater).run {
            setContentView(root)

            val movie = intent.getParcelableExtraCompat<Movie>(MOVIE) ?: throw IllegalStateException()

            movieDetailToolbar.title = movie.title

            val background = movie.backdropPath ?: movie.posterPath
            movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780$background")
            movieDetailSummary.text = movie.overview

            movieDetailInfo.setMovie(movie)
        }
    }
}