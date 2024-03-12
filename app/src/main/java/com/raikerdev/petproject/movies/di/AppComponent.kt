package com.raikerdev.petproject.movies.di

import android.app.Application
import com.raikerdev.petproject.movies.ui.detail.DetailFragment
import com.raikerdev.petproject.movies.ui.main.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AppDataModule::class])
interface AppComponent {

    fun inject(fragment: MainFragment)
    fun inject(fragment: DetailFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }

}
