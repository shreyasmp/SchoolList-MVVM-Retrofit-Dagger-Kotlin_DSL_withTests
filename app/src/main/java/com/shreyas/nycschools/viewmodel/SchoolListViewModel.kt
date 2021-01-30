package com.shreyas.nycschools.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shreyas.nycschools.base.BaseViewModel
import com.shreyas.nycschools.model.School
import com.shreyas.nycschools.repository.NYCSchoolRepository
import com.shreyas.nycschools.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SchoolListViewModel @Inject constructor(repository: NYCSchoolRepository) :
    BaseViewModel(repository) {

    companion object {
        private val TAG = SchoolListViewModel::class.java.simpleName
    }
    
    val _schoolList: MutableLiveData<List<School>> = MutableLiveData()
    val schoolList: LiveData<List<School>> = _schoolList

    fun fetchSchoolList() {
        schoolJob = viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.getNYCSchoolList()
            }
            when (result) {
                is ResultWrapper.SUCCESS -> {
                    _isError.value = false
                    _schoolList.value = result.value.value
                    Log.i(TAG, "School List Response: ${result.value.value}")
                }
                is ResultWrapper.FAILURE -> {
                    _isError.value = true
                    _schoolList.value = null
                }
            }
        }
    }
}