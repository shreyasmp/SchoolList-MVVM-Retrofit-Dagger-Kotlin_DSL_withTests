package com.shreyas.nycschools.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shreyas.nycschools.base.MockServerBaseTest
import com.shreyas.nycschools.service.INYCSchoolService
import com.shreyas.nycschools.utils.ResultWrapper
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class NYCSchoolRepositoryTest : MockServerBaseTest() {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    override fun isMockServerEnabled(): Boolean = true

    private lateinit var repository: NYCSchoolRepository
    private lateinit var service: INYCSchoolService

    @Before
    fun start() {
        service = provideTestNYCSchoolService()
        repository = NYCSchoolRepository(service)
    }

    @Test
    fun `given response as OK when fetch nyc school list results in full list`() {
        runBlocking {
            mockHttpResponseFromFile("schools_list.json", HttpURLConnection.HTTP_OK)
            when (val result = repository.getNYCSchoolList()) {
                is ResultWrapper.SUCCESS -> {
                    val response = result.value.value
                    assertNotNull(response)
                    assertEquals(response?.size, 440)
                }
            }
        }
    }

    @Test
    fun `given response as OK when fetch nyc school list results in empty list`() {
        runBlocking {
            mockHttpResponseFromFile("empty_list.json", HttpURLConnection.HTTP_OK)
            when (val result = repository.getNYCSchoolList()) {
                is ResultWrapper.SUCCESS -> {
                    val response = result.value.value
                    assertNotNull(response)
                    assertEquals(response?.size, 0)
                }
            }
        }
    }

    @Test
    fun `given response as FAILURE while fetch school list results in exception`() {
        runBlocking {
            mockHttpResponse(403)
            when (val result = repository.getNYCSchoolList()) {
                is ResultWrapper.FAILURE -> {
                    assertNotNull(result)
                    val expectedResponse = ResultWrapper.FAILURE(null)
                    assertEquals(expectedResponse.code, (result).code)
                }
            }
        }
    }

    @Test
    fun `given response as OK while fetch school sat scores results in sat scores`() {
        runBlocking {
            mockHttpResponseFromFile("school_sat_scores.json", HttpURLConnection.HTTP_OK)
            when (val result = repository.getNYCSchoolSATScores("01M292")) {
                is ResultWrapper.SUCCESS -> {
                    val response = result.value.value
                    assertNotNull(response)
                    assertEquals(response?.size, 1)
                }
            }
        }
    }

    @Test
    fun `given response as OK while fetch school sat scores results in empty list`() {
        runBlocking {
            mockHttpResponseFromFile("empty_list.json", HttpURLConnection.HTTP_OK)
            when (val result = repository.getNYCSchoolSATScores("01M292")) {
                is ResultWrapper.SUCCESS -> {
                    val response = result.value.value
                    assertNotNull(response)
                    assertEquals(response?.size, 0)
                }
            }
        }
    }

    @Test
    fun `given response as FAILURE while fetch sat score results in exception`() {
        runBlocking {
            mockHttpResponse(403)
            when (val result = repository.getNYCSchoolSATScores("01M292")) {
                is ResultWrapper.FAILURE -> {
                    assertNotNull(result)
                    val expectedResponse = ResultWrapper.FAILURE(null)
                    assertEquals(expectedResponse.code, (result).code)
                }
            }
        }
    }
}