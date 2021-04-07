package com.shreyas.nycschools.view

import android.os.Build
import android.os.Looper.getMainLooper
import android.view.View
import com.google.common.truth.Truth.assertThat
import com.shreyas.nycschools.R
import com.shreyas.nycschools.runner.SchoolRobolectricTestRunner
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(SchoolRobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@LooperMode(LooperMode.Mode.LEGACY)
class MainActivityTest {

    private lateinit var activity: MainActivity

    @Before
    fun setUp() {
        activity = Robolectric.buildActivity(MainActivity::class.java).create().visible().get()
        Shadows.shadowOf(getMainLooper()).idle()
    }

    @Test
    fun `activity is successfully created`() {
        assertThat(activity).isNotNull()
    }

    @Test
    fun `should have correct app name`() {
        assertThat(activity.resources.getString(R.string.app_name)).isEqualTo("NYCSchools")
    }

    @Test
    fun `test activity view components are visible`() {
        assertThat(activity.binding.fragmentContainer.visibility).isEqualTo(View.VISIBLE)
        assertThat(activity.binding.mainLayout.visibility).isEqualTo(View.VISIBLE)
        assertThat(activity.binding.mainToolbar.visibility).isEqualTo(View.VISIBLE)
    }

    @After
    fun tearDown() {
        activity.finish()
    }
}