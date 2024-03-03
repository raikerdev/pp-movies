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
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableExtraCompat<Movie>(MOVIE)?.run {
            binding.movieDetailToolbar.title = title

            val background = backdropPath ?: posterPath
            binding.movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780$background")

            binding.movieDetailSummary.text = overview

            binding.movieDetailInfo.text = buildSpannedString {
                bold { append("Original language: ") }
                appendLine(originalLanguage)

                bold { append("Original title: ") }
                appendLine(originalTitle)

                bold { append("Release date: ") }
                appendLine(releaseDate)

                bold { append("Popularity: ") }
                appendLine(popularity.toString())

                bold { append("Vote Average: ") }
                appendLine(voteAverage.toString())
            }
        }
    }
}