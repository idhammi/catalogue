package id.idham.catalogue.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import id.idham.catalogue.R
import id.idham.catalogue.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(9)
        )
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.txt_name)).check(matches(isDisplayed()))
        onView(withId(R.id.img_photo)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_year)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_language)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_detail_description)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_release_date)).check(matches(isDisplayed()))
    }

    @Test
    fun loadTvShows() {
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(9)
        )
    }

    @Test
    fun loadDetailTvShow() {
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.txt_name)).check(matches(isDisplayed()))
        onView(withId(R.id.img_photo)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_year)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_language)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_detail_description)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_release_date)).check(matches(isDisplayed()))
    }

}