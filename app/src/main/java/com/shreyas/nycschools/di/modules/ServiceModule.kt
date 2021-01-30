package com.shreyas.nycschools.di.modules

import com.shreyas.nycschools.service.INYCSchoolService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object ServiceModule {

    private const val NYC_SCHOOL_BASE_URL = "https://data.cityofnewyork.us/resource/"

    @Provides
    @Reusable
    internal fun provideNYCSchoolService(): INYCSchoolService {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .baseUrl(NYC_SCHOOL_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(INYCSchoolService::class.java)
    }
}