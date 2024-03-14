package com.raikerdev.petproject.movies.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.raikerdev.petproject.apptestshared.buildDatabaseMovies
import com.raikerdev.petproject.data.datasource.MovieRemoteDataSource
import com.raikerdev.petproject.movies.data.database.MovieDao
import com.raikerdev.petproject.movies.data.server.fromJson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception
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

    private lateinit var server: MockWebServer

    @Inject
    lateinit var remoteDataSource: MovieRemoteDataSource

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start(8080)

        server.enqueue(MockResponse().fromJson("popular_movies.json"))

        hiltRule.inject()
    }

    @After
    fun tearDown() {
        server.shutdown()
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

    @Test
    fun check_mock_server_is_working() = runTest {
        val movies = remoteDataSource.findPopularMovies("US")
        movies.fold({throw Exception(it.toString())}) {
            Assert.assertEquals("Poor Things", it[0].title)
        }
    }

}
