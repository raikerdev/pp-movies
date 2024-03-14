package com.raikerdev.petproject.movies.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.raikerdev.petproject.apptestshared.buildDatabaseMovies
import com.raikerdev.petproject.movies.data.database.MovieDao
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
class MainInstrumentationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val locationPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        "android.permission.ACCESS_COARSE_LOCATION"
    )

    @get:Rule(order = 2)
    val activityRule = ActivityScenarioRule(NavHostActivity::class.java)

    @Inject
    lateinit var movieDao: MovieDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_6_items_db() = runTest{
        movieDao.insertMovies(buildDatabaseMovies(5, 6, 7, 8, 9, 10))
        Assert.assertEquals(6, movieDao.movieCount())
    }

    @Test
    fun check_4_items_db() = runTest{
        movieDao.insertMovies(buildDatabaseMovies(1, 2, 3, 4))
        Assert.assertEquals(4, movieDao.movieCount())
    }

}
