package com.raikerdev.petproject.movies

import android.app.Application
import com.raikerdev.petproject.movies.di.AppComponent
import com.raikerdev.petproject.movies.di.DaggerAppComponent

class App: Application() {

    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
            .factory()
            .create(this)

    }

}
