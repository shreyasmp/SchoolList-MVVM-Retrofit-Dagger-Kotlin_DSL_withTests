package com.shreyas.nycschools.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shreyas.nycschools.base.BaseViewModel
import com.shreyas.nycschools.model.School
import com.shreyas.nycschools.model.SchoolSATScores
import com.shreyas.nycschools.repository.NYCSchoolRepository
import com.shreyas.nycschools.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SchoolDetailsViewModel @Inject constructor(repository: NYCSchoolRepository) :
    BaseViewModel(repository) {

    companion object {
        private val TAG = SchoolDetailsViewModel::class.java.simpleName
    }

    val schoolSATScores: MutableLiveData<List<SchoolSATScores>> = MutableLiveData()

    val selectedSchool: MutableLiveData<School> = MutableLiveData()

    fun fetchSchoolSATScores(school: School) {
        schoolJob = viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.getNYCSchoolSATScores(school.dbn)
            }
            when (result) {
                is ResultWrapper.SUCCESS -> {
                    _isError.value = false
                    schoolSATScores.value = result.value.value
                    Log.i(
                        TAG, "School SAT Score Response: ${result.value.value}"
                    )
                }
                is ResultWrapper.FAILURE -> {
                    _isError.value = true
                    schoolSATScores.value = null
                    selectedSchool.value = null
                }
            }
        }
    }
}