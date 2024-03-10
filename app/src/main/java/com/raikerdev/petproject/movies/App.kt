package com.raikerdev.petproject.movies

import android.app.Application

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initDi()
    }

}
