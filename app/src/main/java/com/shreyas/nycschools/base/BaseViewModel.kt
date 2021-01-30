package com.shreyas.nycschools.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shreyas.nycschools.repository.NYCSchoolRepository
import kotlinx.coroutines.Job
import javax.inject.Inject

open class BaseViewModel @Inject constructor(val repository: NYCSchoolRepository) : ViewModel() {

    companion object {
        private val TAG = BaseViewModel::class.java.simpleName
    }

    val _isError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean> = _isError

    var schoolJob: Job? = null
    
    override fun onCleared() {
        super.onCleared()
        schoolJob?.cancel()
        _isError.postValue(false)
    }
}