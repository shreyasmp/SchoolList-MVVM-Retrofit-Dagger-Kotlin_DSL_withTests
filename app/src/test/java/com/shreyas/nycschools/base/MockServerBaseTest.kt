package com.shreyas.nycschools.base

import com.shreyas.nycschools.service.INYCSchoolService
import com.shreyas.nycschools.util.TestJsonUtils.getJsonAsString
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class MockServerBaseTest {

    private lateinit var mockServer: MockWebServer

    @Before
    open fun setup() {
        this.startMockServer()
    }

    abstract fun isMockServerEnabled(): Boolean

    open fun startMockServer() {
        if (isMockServerEnabled()) {
            mockServer = MockWebServer()
            mockServer.start()
        }
    }

    open fun stopMockServer() {
        if (isMockServerEnabled()) {
            mockServer.shutdown()
        }
    }

    @After
    open fun tearDown() {
        this.stopMockServer()
    }

    // Methods to mock responses based on responseCode and response json in assets
    open fun mockHttpResponseFromFile(fileName: String, responseCode: Int) =
        mockServer.enqueue(
            MockResponse().setResponseCode(responseCode).setBody(getJsonAsString(fileName))
        )

    open fun mockHttpResponse(responseCode: Int) =
        mockServer.enqueue(MockResponse().setResponseCode(responseCode))

    // Create a test service
    fun provideTestNYCSchoolService(): INYCSchoolService {
        return Retrofit.Builder().baseUrl(mockServer.url("/")).addConverterFactory(
            GsonConverterFactory.create()
        ).client(OkHttpClient.Builder().build()).build().create(INYCSchoolService::class.java)
    }
}