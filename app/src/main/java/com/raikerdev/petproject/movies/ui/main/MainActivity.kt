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
import com.raikerdev.petproject.movies.model.MoviesRepository
import com.raikerdev.petproject.movies.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val moviesRepository by lazy { MoviesRepository(this) }

    private val adapter = MoviesAdapter {
        Intent(this, DetailActivity::class.java)
            .apply { putExtra(DetailActivity.MOVIE, it) }
            .let { startActivity(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(ActivityMainBinding.inflate(layoutInflater)) {
            setContentView(root)
            recycler.adapter = adapter

            lifecycleScope.launch {
                progress.isVisible = true
                adapter.submitList(moviesRepository.findPopularMovies().results)
                progress.isVisible = false
            }
        }
    }
}