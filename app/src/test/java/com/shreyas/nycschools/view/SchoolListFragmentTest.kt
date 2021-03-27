package com.shreyas.nycschools.view

import android.content.Context
import com.google.common.truth.Truth.assertThat
import com.shreyas.nycschools.runner.SchoolRobolectricTestRunner
import com.shreyas.nycschools.util.TestJsonUtils.startFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito

@RunWith(SchoolRobolectricTestRunner::class)
class SchoolListFragmentTest {

    private lateinit var schoolListFragment: SchoolListFragment
    private var mockContext = Mockito.mock(Context::class.java)

    @Before
    fun setUp() {
        schoolListFragment = SchoolListFragment()
    }

    @Test
    fun `test if school list fragment created`() {
        startFragment(schoolListFragment)
        assertThat(schoolListFragment).isNotNull()
    }

    @Test
    fun `test if school list title is correct`() {
        Mockito.`when`(mockContext.getString(anyInt()))
            .thenReturn("NYCSchools List")
    }
}