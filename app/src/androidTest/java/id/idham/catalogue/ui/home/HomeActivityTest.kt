package id.idham.catalogue.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
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
        onView(withId(R.id.menu_movie)).perform(click())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5)
        )
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.menu_movie)).perform(click())
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
    fun addMovieToFavorite() {
        onView(withId(R.id.menu_movie)).perform(click())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.action_favorite)).perform(click())
        pressBack()
        onView(withId(R.id.menu_favorite)).perform(click())
    }

    @Test
    fun loadTvShows() {
        onView(withId(R.id.menu_tv_show)).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5)
        )
    }

    @Test
    fun loadDetailTvShow() {
        onView(withId(R.id.menu_tv_show)).perform(click())
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

    @Test
    fun addTvShowToFavorite() {
        onView(withId(R.id.menu_tv_show)).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.action_favorite)).perform(click())
        pressBack()
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager)).perform(swipeLeft())
    }

}