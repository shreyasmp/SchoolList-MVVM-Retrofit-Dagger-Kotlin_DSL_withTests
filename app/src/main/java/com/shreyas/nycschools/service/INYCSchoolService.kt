package com.shreyas.nycschools.service

import com.shreyas.nycschools.model.School
import com.shreyas.nycschools.model.SchoolSATScores
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface INYCSchoolService {

    @Headers("Content-Type: application/json")
    @GET(value = "s3k6-pzi2.json")
    suspend fun fetchNYCSchoolList(): Response<List<School>>

    @Headers("Content-Type: application/json")
    @GET(value = "f9bf-2cp4.json")
    suspend fun fetchNYCSchoolSATScores(@Query("dbn") dbname: String): Response<List<SchoolSATScores>>
}