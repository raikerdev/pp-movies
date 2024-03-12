package com.raikerdev.petproject.movies.di

import android.app.Application
import com.raikerdev.petproject.movies.ui.detail.DetailViewModelFactory
import com.raikerdev.petproject.movies.ui.main.MainViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, UseCasesModule::class,
    DataModule::class, ViewModelsModule::class])
interface AppComponent {

    val mainViewModelFactory: MainViewModelFactory
    val detailViewModelFactory: DetailViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }

}
