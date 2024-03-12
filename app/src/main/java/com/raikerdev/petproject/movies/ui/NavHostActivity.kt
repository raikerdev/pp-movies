package com.raikerdev.petproject.movies.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raikerdev.petproject.movies.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavHostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_host)
    }
}
