package com.raikerdev.petproject.movies.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.raikerdev.petproject.movies.R
import com.raikerdev.petproject.movies.databinding.ActivityMainBinding
import com.raikerdev.petproject.movies.model.MoviesRepository
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
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.recycler.adapter = adapter
        lifecycleScope.launch {
            adapter.movies = moviesRepository.findPopularMovies().results
        }
    }
}