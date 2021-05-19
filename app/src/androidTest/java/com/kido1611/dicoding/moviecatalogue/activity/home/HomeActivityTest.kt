package com.kido1611.dicoding.moviecatalogue.activity.home

//import androidx.recyclerview.widget.RecyclerView
//import androidx.test.core.app.ActivityScenario
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.IdlingRegistry
//import androidx.test.espresso.action.ViewActions
//import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.contrib.RecyclerViewActions
//import androidx.test.espresso.matcher.ViewMatchers.*
//import com.kido1611.dicoding.moviecatalogue.R
//import com.kido1611.dicoding.moviecatalogue.utils.EspressoIdlingResource
//import org.junit.After
//import org.junit.Before
//import org.junit.Test

class HomeActivityTest {
//    @Before
//    fun setUp() {
//        ActivityScenario.launch(HomeActivity::class.java)
//        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
//    }
//
//    @After
//    fun tearDown() {
//        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
//    }
//
//    @Test
//    fun loadMovies() {
//        onView(withText("MOVIES")).apply {
//            check(matches(isDisplayed()))
//            perform(ViewActions.click())
//        }
//
//        onView(withId(R.id.rv_movies)).apply {
//            check(matches(isDisplayed()))
//            perform(
//                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10)
//            )
//        }
//    }
//
//    @Test
//    fun loadTvs() {
//        onView(withText("TV SHOWS")).apply {
//            check(matches(isDisplayed()))
//            perform(ViewActions.click())
//        }
//
//        onView(withId(R.id.rv_tvs)).apply {
//            check(matches(isDisplayed()))
//            perform(
//                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10)
//            )
//        }
//    }
//
//    @Test
//    fun loadDetailMovie() {
//        onView(withText("MOVIES")).apply {
//            check(matches(isDisplayed()))
//            perform(ViewActions.click())
//        }
//
//        onView(withId(R.id.rv_movies)).apply {
//            check(matches(isDisplayed()))
//            perform(
//                RecyclerViewActions
//                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(
//                        10,
//                        ViewActions.click()
//                    )
//            )
//        }
//
//        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
//        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))
//        onView(withId(R.id.iv_backdrop)).check(matches(isDisplayed()))
//    }
//
//    @Test
//    fun loadDetailTv() {
//        onView(withText("TV SHOWS")).apply {
//            check(matches(isDisplayed()))
//            perform(ViewActions.click())
//        }
//
//        onView(withId(R.id.rv_tvs)).apply {
//            check(matches(isDisplayed()))
//            perform(
//                RecyclerViewActions
//                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(
//                        10,
//                        ViewActions.click()
//                    )
//            )
//        }
//
//        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
//        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))
//        onView(withId(R.id.iv_backdrop)).check(matches(isDisplayed()))
//    }
}