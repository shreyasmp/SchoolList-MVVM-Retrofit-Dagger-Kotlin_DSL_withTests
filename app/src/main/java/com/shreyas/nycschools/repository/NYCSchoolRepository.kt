package com.shreyas.nycschools.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shreyas.nycschools.model.School
import com.shreyas.nycschools.model.SchoolSATScores
import com.shreyas.nycschools.service.INYCSchoolService
import com.shreyas.nycschools.utils.ResultWrapper
import javax.inject.Inject
import javax.inject.Singleton

interface INYCSchoolRepository {

    suspend fun getNYCSchoolList(): ResultWrapper<LiveData<List<School>>>

    suspend fun getNYCSchoolSATScores(schoolDBName: String): ResultWrapper<LiveData<List<SchoolSATScores>>>
}

/**
 *  Singleton repository that takes service interface in constructor
 *  for DI injection
 */
@Singleton
open class NYCSchoolRepository @Inject constructor(
    private val service: INYCSchoolService
) : INYCSchoolRepository {

    companion object {
        private val TAG = NYCSchoolRepository::class.java.simpleName
    }

    override suspend fun getNYCSchoolList(): ResultWrapper<LiveData<List<School>>> {
        val schoolList: MutableLiveData<List<School>> = MutableLiveData()
        val deferredResponse = service.fetchNYCSchoolList()
        return try {
            if (deferredResponse.isSuccessful && deferredResponse.body()?.size != 0) {
                schoolList.postValue(deferredResponse.body())
                ResultWrapper.SUCCESS(schoolList)
            } else {
                ResultWrapper.FAILURE(null)
            }
        } catch (exception: Exception) {
            Log.d(TAG, "Exception: ${exception.message}")
            ResultWrapper.FAILURE(null)
        }
    }

    override suspend fun getNYCSchoolSATScores(schoolDBName: String): ResultWrapper<LiveData<List<SchoolSATScores>>> {
        val schoolSatScoresData: MutableLiveData<List<SchoolSATScores>> = MutableLiveData()
        val deferredResponse = service.fetchNYCSchoolSATScores(schoolDBName)
        return try {
            if (deferredResponse.isSuccessful && deferredResponse.body()?.size != 0) {
                schoolSatScoresData.postValue(deferredResponse.body())
                ResultWrapper.SUCCESS(schoolSatScoresData)
            } else {
                ResultWrapper.FAILURE(null)
            }
        } catch (exception: Exception) {
            Log.d(TAG, "Exception: ${exception.message}")
            ResultWrapper.FAILURE(null)
        }
    }
}