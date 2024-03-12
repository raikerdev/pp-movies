package com.raikerdev.petproject.movies.di

import android.app.Application
import com.raikerdev.petproject.movies.ui.detail.DetailFragmentComponent
import com.raikerdev.petproject.movies.ui.detail.DetailFragmentModule
import com.raikerdev.petproject.movies.ui.main.MainFragmentComponent
import com.raikerdev.petproject.movies.ui.main.MainFragmentModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AppDataModule::class])
interface AppComponent {

    fun plus(mainFragmentModule: MainFragmentModule): MainFragmentComponent
    fun plus(detailFragmentModule: DetailFragmentModule): DetailFragmentComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }

}
