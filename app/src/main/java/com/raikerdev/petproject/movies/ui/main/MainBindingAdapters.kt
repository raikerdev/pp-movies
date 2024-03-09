package com.raikerdev.petproject.movies.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raikerdev.petproject.movies.domain.Movie

@BindingAdapter("movies")
fun RecyclerView.setItems(movies: List<Movie>?) {
    movies?.let {
        (adapter as? MoviesAdapter)?.submitList(it)
    }
}
