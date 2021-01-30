package com.shreyas.nycschools.util

import androidx.lifecycle.LiveData

fun <T> LiveData<T>.testObserver() = TestObserver<T>().also { testObserver ->
    observeForever(testObserver)
}