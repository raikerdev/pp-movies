package com.raikerdev.petproject.movies

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.raikerdev.petproject.data.DataModule
import com.raikerdev.petproject.movies.data.database.MovieDatabase
import com.raikerdev.petproject.usecases.UseCasesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.ksp.generated.module

fun Application.initDi() {
    startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@initDi)
        modules(AppModule().module, DataModule().module, UseCasesModule().module)
    }
}

@Module
@ComponentScan
class AppModule {
    @Single
    @Named("apiKey")
    fun apiKey(context: Context) = context.getString(R.string.api_key)

    @Single
    fun movieDatabase(context: Context) = Room.databaseBuilder(
        context,
        MovieDatabase::class.java, "movie-db"
    ).build()

    @Single
    fun movieDao(db: MovieDatabase) = db.movieDao()

}
