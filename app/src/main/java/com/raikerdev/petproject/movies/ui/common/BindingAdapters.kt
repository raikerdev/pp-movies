package com.raikerdev.petproject.movies.ui.common

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("url")
fun ImageView.setImageUrl(url: String?){
    url?.let { loadUrl(it) }
}

@BindingAdapter("isVisible")
fun View.setVisible(visible: Boolean?){
    isVisible = visible == true
}