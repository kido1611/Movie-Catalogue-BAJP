package com.kido1611.dicoding.moviecatalogue.activity.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.kido1611.dicoding.moviecatalogue.R
import com.kido1611.dicoding.moviecatalogue.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HomeActivityTest {
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
        onView(withText("MOVIES")).apply {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.rv_movies)).apply {
            check(matches(isDisplayed()))
            perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10)
            )
        }
    }

    @Test
    fun loadTvs() {
        onView(withText("TV SHOWS")).apply {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.rv_tvs)).apply {
            check(matches(isDisplayed()))
            perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10)
            )
        }
    }

    @Test
    fun loadDetailMovie() {
        onView(withText("MOVIES")).apply {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.rv_movies)).apply {
            check(matches(isDisplayed()))
            perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        10,
                        click()
                    )
            )
        }

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_backdrop)).check(matches(isDisplayed()))
    }

    @Test
    fun loadDetailTv() {
        onView(withText("TV SHOWS")).apply {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.rv_tvs)).apply {
            check(matches(isDisplayed()))
            perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        10,
                        click()
                    )
            )
        }

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_backdrop)).check(matches(isDisplayed()))
    }

    @Test
    fun loadBookmarkedMovies() {
        onView(withId(R.id.menu_bookmark)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withText("Simpan"))
            .check(matches(isDisplayed()))
        onView(withText("MOVIES")).apply {
            check(matches(isDisplayed()))
            perform(click())
        }
        onView(withId(R.id.rv_movies))
            .check(matches(isDisplayed()))
    }

    @Test
    fun loadBookmarkedTvs() {
        onView(withId(R.id.menu_bookmark)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withText("Simpan"))
            .check(matches(isDisplayed()))
        onView(withText("TV SHOWS")).apply {
            check(matches(isDisplayed()))
            perform(click())
        }
        onView(withId(R.id.rv_tvs))
            .check(matches(isDisplayed()))
    }

    @Test
    fun loadMovieDetailAndToggleBookmark() {
        onView(withText("MOVIES")).apply {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.rv_movies)).apply {
            check(matches(isDisplayed()))
            perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        10,
                        click()
                    )
            )
        }

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_backdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite)).apply {
            check(matches(isDisplayed()))
            check(ViewTagAssertion("un-bookmarked"))
            perform(click())
            check(ViewTagAssertion("bookmarked"))
            perform(click())
            check(ViewTagAssertion("un-bookmarked"))
        }
    }

    @Test
    fun loadTvDetailAndToggleBookmark() {
        onView(withText("TV SHOWS")).apply {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.rv_tvs)).apply {
            check(matches(isDisplayed()))
            perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        10,
                        click()
                    )
            )
        }

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_backdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite)).apply {
            check(matches(isDisplayed()))
            check(ViewTagAssertion("un-bookmarked"))
            perform(click())
            check(ViewTagAssertion("bookmarked"))
            perform(click())
            check(ViewTagAssertion("un-bookmarked"))
        }
    }

    @Test
    fun loadBookmarkMovieAndUnBookmarkFromBookmarkList() {
        onView(withText("MOVIES")).apply {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.rv_movies)).apply {
            check(matches(isDisplayed()))
            perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        5,
                        click()
                    )
            )
        }

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_backdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite)).apply {
            check(matches(isDisplayed()))
            check(ViewTagAssertion("un-bookmarked"))
            perform(click())
            check(ViewTagAssertion("bookmarked"))
        }

        pressBack()

        onView(withId(R.id.menu_bookmark)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withText("Simpan"))
            .check(matches(isDisplayed()))
        onView(withText("MOVIES")).apply {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.rv_movies)).apply {
            check(matches(isDisplayed()))
            perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0,
                        click()
                    )
            )
        }

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_backdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite)).apply {
            check(matches(isDisplayed()))
            check(ViewTagAssertion("bookmarked"))
            perform(click())
            check(ViewTagAssertion("un-bookmarked"))
        }
    }

    @Test
    fun loadBookmarkTvAndUnBookmarkFromBookmarkList() {
        onView(withText("TV SHOWS")).apply {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.rv_tvs)).apply {
            check(matches(isDisplayed()))
            perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        5,
                        click()
                    )
            )
        }

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_backdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite)).apply {
            check(matches(isDisplayed()))
            check(ViewTagAssertion("un-bookmarked"))
            perform(click())
            check(ViewTagAssertion("bookmarked"))
        }

        pressBack()

        onView(withId(R.id.menu_bookmark)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withText("Simpan"))
            .check(matches(isDisplayed()))
        onView(withText("TV SHOWS")).apply {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.rv_tvs)).apply {
            check(matches(isDisplayed()))
            perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0,
                        click()
                    )
            )
        }

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_backdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite)).apply {
            check(matches(isDisplayed()))
            check(ViewTagAssertion("bookmarked"))
            perform(click())
            check(ViewTagAssertion("un-bookmarked"))
        }
    }

    @Test
    fun testRefreshMovies() {
        onView(withText("MOVIES")).apply {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))

        onView(withId(R.id.swipe_refresh_layout_movies)).apply {
            check(matches(isDisplayed()))
            perform(ViewActions.swipeDown())
        }
    }

    @Test
    fun testRefreshTvs() {
        onView(withText("TV SHOWS")).apply {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.rv_tvs)).check(matches(isDisplayed()))

        onView(withId(R.id.swipe_refresh_layout_tvs)).apply {
            check(matches(isDisplayed()))
            perform(ViewActions.swipeDown())
        }
    }

    @Test
    fun testRefreshBookmarkedMovies() {
        onView(withId(R.id.menu_bookmark)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withText("Simpan"))
            .check(matches(isDisplayed()))

        onView(withText("MOVIES")).apply {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))

        onView(withId(R.id.swipe_refresh_layout_movies)).apply {
            check(matches(isDisplayed()))
            perform(ViewActions.swipeDown())
        }
    }

    @Test
    fun testRefreshBookmarkedTvs() {
        onView(withId(R.id.menu_bookmark)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withText("Simpan"))
            .check(matches(isDisplayed()))

        onView(withText("TV SHOWS")).apply {
            check(matches(isDisplayed()))
            perform(click())
        }

        onView(withId(R.id.rv_tvs)).check(matches(isDisplayed()))

        onView(withId(R.id.swipe_refresh_layout_tvs)).apply {
            check(matches(isDisplayed()))
            perform(ViewActions.swipeDown())
        }
    }
}

class ViewTagAssertion(
    private val tagValue: Any
) : ViewAssertion {
    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        noViewFoundException?.let { throw noViewFoundException }

        Assert.assertNotNull(view)
        Assert.assertEquals(view!!.tag, tagValue)
    }
}