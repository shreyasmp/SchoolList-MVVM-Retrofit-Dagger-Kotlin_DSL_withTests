package com.shreyas.nycschools.view

import android.os.SystemClock
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.shreyas.nycschools.R
import com.shreyas.nycschools.view.adapter.SchoolRecyclerViewAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SchoolListFragmentAndroidTest {

    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_initial_state_school_details() {
        activityTestRule.scenario.moveToState(Lifecycle.State.RESUMED)
        // Adding delay to ensure data is loaded since its a lot of schools to load
        SystemClock.sleep(3000)
        with(onView(withId(R.id.school_list))) {
            perform(
                RecyclerViewActions.scrollToPosition<SchoolRecyclerViewAdapter.SchoolViewHolder>(
                    1
                )
            )
            perform(click())
        }
    }

    @Test
    fun test_scroll_to_twelfth_element_and_click() {
        activityTestRule.scenario.moveToState(Lifecycle.State.RESUMED)
        SystemClock.sleep(3000)
        with(onView(withId(R.id.school_list))) {
            perform(
                RecyclerViewActions.actionOnItemAtPosition<SchoolRecyclerViewAdapter.SchoolViewHolder>(
                    12,
                    click()
                )
            )
        }
    }
}