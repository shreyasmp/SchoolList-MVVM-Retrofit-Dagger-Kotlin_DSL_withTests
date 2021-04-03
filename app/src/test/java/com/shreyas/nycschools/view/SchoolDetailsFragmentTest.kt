package com.shreyas.nycschools.view

import com.google.common.truth.Truth.assertThat
import com.shreyas.nycschools.runner.SchoolRobolectricTestRunner
import com.shreyas.nycschools.util.TestJsonUtils.startFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(SchoolRobolectricTestRunner::class)
class SchoolDetailsFragmentTest {

    private lateinit var schoolDetailsFragment: SchoolDetailsFragment

    @Before
    fun setUp() {
        schoolDetailsFragment = SchoolDetailsFragment()
    }

    @Test
    fun `test if school detail fragment created`() {
        startFragment(schoolDetailsFragment)
        assertThat(schoolDetailsFragment).isNotNull()
    }
}