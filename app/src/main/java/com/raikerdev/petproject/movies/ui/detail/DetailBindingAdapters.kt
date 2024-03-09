package com.raikerdev.petproject.movies.ui.detail

import androidx.databinding.BindingAdapter
import com.raikerdev.petproject.movies.model.database.Movie

@BindingAdapter("movie")
fun MovieDetailInfoView.setInfoMovie(movie: Movie?){
    movie?.let { setMovie(it) }
}
