package com.raikerdev.petproject.movies.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.raikerdev.petproject.movies.R
import com.raikerdev.petproject.movies.databinding.ActivityMainBinding
import com.raikerdev.petproject.movies.model.Movie
import com.raikerdev.petproject.movies.model.MoviesRepository
import com.raikerdev.petproject.movies.ui.detail.DetailActivity

class MainActivity : AppCompatActivity(), MainPresenter.View {

    private val presenter by lazy { MainPresenter(MoviesRepository(this), lifecycleScope) }

    private val adapter = MoviesAdapter { presenter.onMovieClicked(it) }
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
        presenter.onCreate(this@MainActivity)
        binding.recycler.adapter = adapter
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showProgress() {
        binding.progress.isVisible = true
    }

    override fun hideProgress() {
        binding.progress.isVisible = false
    }

    override fun updateData(movies: List<Movie>) {
        adapter.submitList(movies)
    }

    override fun navigateTo(movie: Movie) {
        Intent(this, DetailActivity::class.java)
            .apply { putExtra(DetailActivity.MOVIE, movie) }
            .let { startActivity(it) }
    }
}