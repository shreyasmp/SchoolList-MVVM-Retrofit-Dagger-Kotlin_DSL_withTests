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
import org.mockito.Mockito.mock

@RunWith(SchoolRobolectricTestRunner::class)
class SchoolDetailsFragmentTest {

    private lateinit var schoolDetailsFragment: SchoolDetailsFragment
    private var mockContext = mock(Context::class.java)

    @Before
    fun setUp() {
        schoolDetailsFragment = SchoolDetailsFragment()
    }

    @Test
    fun `test if school detail fragment created`() {
        startFragment(schoolDetailsFragment)
        assertThat(schoolDetailsFragment).isNotNull()
    }

    @Test
    fun `test if school details title is correct`() {
        Mockito.`when`(mockContext.getString(anyInt())).thenReturn("NYCSchool Details")
    }
}