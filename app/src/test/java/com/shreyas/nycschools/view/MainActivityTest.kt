package com.shreyas.nycschools.view

import android.os.Build
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityTest {

    private lateinit var activity: MainActivity

    @Before
    fun setUp() {
        activity = Robolectric.buildActivity(MainActivity::class.java).create().visible().get()
    }

    @Test
    fun `activity is successfully created`() {
        assertThat(activity).isNotNull()
    }

    @Test
    fun `recreate activity`() {
        val test = launch(MainActivity::class.java)
        test.recreate()
    }
}