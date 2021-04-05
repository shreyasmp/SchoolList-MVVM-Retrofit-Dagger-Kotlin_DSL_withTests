package com.shreyas.nycschools.viewmodel

import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.shreyas.nycschools.base.BaseViewModelTest
import com.shreyas.nycschools.model.School
import com.shreyas.nycschools.model.SchoolSATScores
import com.shreyas.nycschools.util.TestJsonUtils.getJson
import com.shreyas.nycschools.util.TestJsonUtils.getObjectFromJsonFile
import com.shreyas.nycschools.util.testObserver
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.robolectric.Shadows
import retrofit2.HttpException

@ExperimentalCoroutinesApi
class SchoolDetailsViewModelTest : BaseViewModelTest() {

    @SpyK
    private lateinit var viewModel: SchoolDetailsViewModel

    @Mock
    private lateinit var schoolSATScoresResponseObserver: Observer<List<SchoolSATScores>>

    @Before
    override fun setup() {
        super.setup()
        viewModel = SchoolDetailsViewModel(repository)
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `view model is not null`() {
        assertThat(viewModel).isNotNull()
    }

    @Test
    fun `school SAT scores live data is null`() {
        val schoolSATLiveData = viewModel.schoolSATScores.testObserver()
        assertThat(schoolSATLiveData.observedValues).isEmpty()
    }

    @Test
    fun `school selected school live data is null`() {
        val selectedSchoolLiveData = viewModel.selectedSchool.testObserver()
        assertThat(selectedSchoolLiveData.observedValues).isEmpty()
    }

    @Test
    fun `test school sat live scores live data success`() {
        val selectedSchoolSATScores: List<SchoolSATScores>? = getJson(
            fileName = "school_sat_scores.json",
            tClass = SchoolSATScores::class.java
        )
        viewModel.schoolSATScores.postValue(selectedSchoolSATScores)
        val schoolSATScoresLiveData = viewModel.schoolSATScores.testObserver()
        assertThat(schoolSATScoresLiveData.observedValues).containsExactly(selectedSchoolSATScores)
    }

    @Test
    fun `test selected school live data success`() {
        val selectedSchool: School? =
            getObjectFromJsonFile("selected_school.json", School::class.java)
        viewModel.selectedSchool.postValue(selectedSchool)
        val selectedSchoolLiveData = viewModel.selectedSchool.testObserver()
        assertThat(selectedSchoolLiveData.observedValues).containsExactly(selectedSchool)
    }

    @Test
    fun `test selected school live data with error`() {
        val selectedSchool = Gson().fromJson("", School::class.java)
        viewModel.selectedSchool.value = selectedSchool
        val selectedSchoolLiveData = viewModel.selectedSchool.testObserver()
        assertThat(selectedSchoolLiveData.observedValues).containsExactly(selectedSchool)
    }

    @Test
    fun `fetch selected school sat scores http call success`() {
        val schoolSATScore = MutableLiveData<List<SchoolSATScores>>().value
        val selectedSchool: School? =
            getObjectFromJsonFile("selected_school.json", School::class.java)
        coroutineTestRule.runBlockingTest {
            viewModel.schoolSATScores.observeForever(schoolSATScoresResponseObserver)
            whenever(repository.getNYCSchoolSATScores(any())).thenAnswer {
                schoolSATScore
            }
            viewModel.fetchSchoolSATScores(selectedSchool!!)
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            assertThat(viewModel.schoolSATScores.value).isNull()
            assertThat(viewModel.schoolSATScores.value).isEqualTo(schoolSATScore)
        }
    }

    @Test
    fun `fetch selected school sat scores http call error`() {
        val exception = mock(HttpException::class.java)
        val selectedSchool: School? =
            getObjectFromJsonFile("selected_school.json", School::class.java)
        coroutineTestRule.runBlockingTest {
            viewModel.schoolSATScores.observeForever(schoolSATScoresResponseObserver)
            whenever(repository.getNYCSchoolSATScores(any())).thenAnswer {
                exception.message()
            }
            viewModel.fetchSchoolSATScores(selectedSchool!!)
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            assertThat(viewModel.schoolSATScores.value).isNull()
            assertThat(viewModel.schoolSATScores.value).isEqualTo(exception.message())
        }
    }

    @After
    override fun tearDown() {
        super.tearDown()
        viewModel.schoolSATScores.removeObserver(schoolSATScoresResponseObserver)
    }
}