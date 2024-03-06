package com.raikerdev.petproject.movies.ui.detail

import com.raikerdev.petproject.movies.model.Movie

class DetailPresenter {

    interface View {
        fun updateUI(movie: Movie)
    }

    private var view: View? = null

    fun onCreate(view: View, movie: Movie) {
        this.view = view
        view.updateUI(movie)
    }

    fun onDestroy() {
        view = null
    }

}