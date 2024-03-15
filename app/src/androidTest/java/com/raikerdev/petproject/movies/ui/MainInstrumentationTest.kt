package com.raikerdev.petproject.movies.ui

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.raikerdev.petproject.data.datasource.MovieRemoteDataSource
import com.raikerdev.petproject.movies.R
import com.raikerdev.petproject.movies.data.server.MockWebServerRule
import com.raikerdev.petproject.movies.data.server.OkHttp3IdlingResource
import com.raikerdev.petproject.movies.data.server.fromJson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.junit.After
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
    val locationPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant("android.permission.ACCESS_COARSE_LOCATION")

    @get:Rule(order = 2)
    val mockWebServerRule = MockWebServerRule()

    @get:Rule(order = 3)
    val activityRule = ActivityScenarioRule(NavHostActivity::class.java)

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var remoteDataSource: MovieRemoteDataSource

    private lateinit var okHttp3IdlingResource: OkHttp3IdlingResource

    @Before
    fun setUp() {
        mockWebServerRule.server
            .enqueue(MockResponse().fromJson("popular_movies.json"))

        hiltRule.inject()
        okHttp3IdlingResource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(okHttp3IdlingResource)
    }

    @After
    fun tearDown() {
       IdlingRegistry.getInstance().unregister(okHttp3IdlingResource)
    }

    @Test
    fun click_a_movie_navigates_to_detail() {
        Thread.sleep(500)
        onView(withId(R.id.recycler))
            .perform(actionOnItemAtPosition<ViewHolder>(4, click()))

        onView(withId(R.id.movie_detail_toolbar))
            .check(matches(hasDescendant(withText("Wonka"))))
    }

}
