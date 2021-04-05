package com.shreyas.nycschools.viewmodel

import android.os.Looper.getMainLooper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.whenever
import com.shreyas.nycschools.base.BaseViewModelTest
import com.shreyas.nycschools.model.School
import com.shreyas.nycschools.util.TestJsonUtils.getJson
import com.shreyas.nycschools.util.testObserver
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.robolectric.Shadows.shadowOf
import retrofit2.HttpException

@ExperimentalCoroutinesApi
class SchoolListViewModelTest : BaseViewModelTest() {

    @SpyK
    private lateinit var viewModel: SchoolListViewModel

    @Mock
    private lateinit var schoolListResponseObserver: Observer<List<School>>

    @Before
    override fun setup() {
        super.setup()
        viewModel = SchoolListViewModel(repository)
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `view model is not null`() {
        assertThat(viewModel).isNotNull()
    }

    @Test
    fun `school list live data is null`() {
        val schoolListLiveData = viewModel.schoolList.testObserver()
        assertThat(schoolListLiveData.observedValues).isEmpty()
    }

    @Test
    fun `test school list live data success`() {
        val schoolList = getJson(
            fileName = "schools_list.json",
            tClass = School::class.java
        )
        viewModel._schoolList.value = schoolList as MutableList<School>
        val schoolListLiveData = viewModel.schoolList.testObserver()
        assertThat(schoolListLiveData.observedValues).containsExactly(schoolList)
    }

    @Test
    fun `fetch school list live data with error`() {
        val schoolList = null
        viewModel._schoolList.value = schoolList
        val schoolListLiveData = viewModel.schoolList.testObserver()
        assertThat(schoolListLiveData.observedValues).containsExactly(schoolList)
    }

    @Test
    fun `fetch school list http call success`() {
        val schoolList = MutableLiveData<List<School>>().value
        coroutineTestRule.runBlockingTest {
            viewModel.schoolList.observeForever(schoolListResponseObserver)
            whenever(repository.getNYCSchoolList()).thenAnswer {
                schoolList
            }
            viewModel.fetchSchoolList()
            shadowOf(getMainLooper()).idle()
            assertThat(viewModel.schoolList.value).isNull()
            assertThat(viewModel.schoolList.value).isEqualTo(schoolList)
        }
    }

    @Test
    fun `fetch school list http call error`() {
        val exception = mock(HttpException::class.java)
        coroutineTestRule.runBlockingTest {
            viewModel.schoolList.observeForever(schoolListResponseObserver)
            whenever(repository.getNYCSchoolList()).thenAnswer {
                exception.message()
            }
            viewModel.fetchSchoolList()
            shadowOf(getMainLooper()).idle()
            assertThat(viewModel.schoolList.value).isNull()
            assertThat(viewModel.schoolList.value).isEqualTo(exception.message())
        }
    }

    @After
    override fun tearDown() {
        super.tearDown()
        viewModel.schoolList.removeObserver(schoolListResponseObserver)
    }
}