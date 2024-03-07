package com.raikerdev.petproject.movies.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.raikerdev.petproject.movies.R
import com.raikerdev.petproject.movies.databinding.ActivityMainBinding
import com.raikerdev.petproject.movies.model.Movie
import com.raikerdev.petproject.movies.model.MoviesRepository
import com.raikerdev.petproject.movies.ui.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(MoviesRepository(this)) }

    private val adapter = MoviesAdapter { viewModel.onMovieClicked(it) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.recycler.adapter = adapter
        viewModel.state.observe(this, ::updateUI)
    }

    private fun updateUI(state: MainViewModel.UiState) {
        binding.progress.isVisible = state.loading
        state.movies?.let(adapter::submitList)
        state.navigateTo?.let(::navigateTo)
    }

    private fun navigateTo(movie: Movie) {
        Intent(this, DetailActivity::class.java)
            .apply { putExtra(DetailActivity.MOVIE, movie) }
            .let { startActivity(it) }
    }
}