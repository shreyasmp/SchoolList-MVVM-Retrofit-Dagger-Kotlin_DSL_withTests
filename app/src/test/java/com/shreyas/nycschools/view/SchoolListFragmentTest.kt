package com.shreyas.nycschools.view

import com.google.common.truth.Truth.assertThat
import com.shreyas.nycschools.runner.SchoolRobolectricTestRunner
import com.shreyas.nycschools.util.TestJsonUtils.startFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(SchoolRobolectricTestRunner::class)
class SchoolListFragmentTest {

    private lateinit var schoolListFragment: SchoolListFragment

    @Before
    fun setUp() {
        schoolListFragment = SchoolListFragment()
    }

    @Test
    fun `test if school list fragment created`() {
        startFragment(schoolListFragment)
        assertThat(schoolListFragment).isNotNull()
    }
}