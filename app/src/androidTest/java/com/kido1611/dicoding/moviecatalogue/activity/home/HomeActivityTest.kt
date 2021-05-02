package com.kido1611.dicoding.moviecatalogue.activity.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.kido1611.dicoding.moviecatalogue.R
import com.kido1611.dicoding.moviecatalogue.utils.DataDummy
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {
    private val dummyMovies = DataDummy.generateDummyMovies()
    private val dummyTvs = DataDummy.generateDummyTv()

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun loadMovies() {
        onView(withText("MOVIES")).apply {
            check(matches(isDisplayed()))
            perform(ViewActions.click())
        }

        onView(withId(R.id.rv_movies)).apply {
            check(matches(isDisplayed()))
            perform(
                    RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovies.size)
            )
        }
    }

    @Test
    fun loadTvs() {
        onView(withText("TV SHOWS")).apply {
            check(matches(isDisplayed()))
            perform(ViewActions.click())
        }

        onView(withId(R.id.rv_tvs)).apply {
            check(matches(isDisplayed()))
            perform(
                    RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvs.size)
            )
        }
    }

    @Test
    fun loadDetailMovie() {
        onView(withText("MOVIES")).apply {
            check(matches(isDisplayed()))
            perform(ViewActions.click())
        }

        onView(withId(R.id.rv_movies)).apply {
            check(matches(isDisplayed()))
            perform(
                    RecyclerViewActions
                            .actionOnItemAtPosition<RecyclerView.ViewHolder>(
                                    0,
                                    ViewActions.click()
                            )
            )
        }

        onView(withId(R.id.tv_title)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(dummyMovies[0].getMovieTitle())))
        }

        onView(withId(R.id.tv_release_date)).apply {
            check(matches(isDisplayed()))
            check(matches(withText("Rilis tanggal ${dummyMovies[0].getMovieReleaseDate()}")))
        }
    }

    @Test
    fun loadDetailTv() {
        onView(withText("TV SHOWS")).apply {
            check(matches(isDisplayed()))
            perform(ViewActions.click())
        }

        onView(withId(R.id.rv_tvs)).apply {
            check(matches(isDisplayed()))
            perform(
                    RecyclerViewActions
                            .actionOnItemAtPosition<RecyclerView.ViewHolder>(
                                    0,
                                    ViewActions.click()
                            )
            )
        }

        onView(withId(R.id.tv_title)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(dummyTvs[0].getMovieTitle())))
        }

        onView(withId(R.id.tv_release_date)).apply {
            check(matches(isDisplayed()))
            check(matches(withText("Rilis tanggal ${dummyTvs[0].getMovieReleaseDate()}")))
        }
    }
}