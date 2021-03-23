package com.shreyas.nycschools.di

import com.google.common.truth.Truth.assertThat
import com.shreyas.nycschools.MainApplication
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import java.time.Instant

class SchoolModuleTest {

    private val mockApplication = mockk<MainApplication>()
    private lateinit var module: SchoolModule

    @Before
    fun setUp() {
        module = FakeSchoolModule()
    }

    @Test
    fun `test provide context returns application context`() {
        every { mockApplication.applicationContext } returns mockApplication

        val result = SchoolModule.provideApplicationContext(mockApplication)

        assertThat(result).isEqualTo(mockApplication)
        verify { mockApplication.applicationContext }

        Instant.now()
    }

    class FakeSchoolModule : SchoolModule() {

    }
}