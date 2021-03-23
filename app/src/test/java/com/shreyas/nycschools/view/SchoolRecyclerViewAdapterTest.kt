package com.shreyas.nycschools.view

import android.view.View
import android.widget.LinearLayout
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.google.common.truth.Truth.assertThat
import com.shreyas.nycschools.model.School
import com.shreyas.nycschools.runner.SchoolRobolectricTestRunner
import com.shreyas.nycschools.util.TestJsonUtils.getJsonAsString
import com.shreyas.nycschools.util.TestJsonUtils.getObjectList
import com.shreyas.nycschools.view.adapter.SchoolRecyclerViewAdapter
import com.shreyas.nycschools.view.callback.SchoolListItemClickListener
import io.mockk.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.annotation.LooperMode

@RunWith(SchoolRobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.LEGACY)
class SchoolRecyclerViewAdapterTest {

    private val mockSchoolListAdapter = mockk<SchoolRecyclerViewAdapter>()
    private val mockSchoolListAdapterHolder =
        mock(SchoolRecyclerViewAdapter.SchoolViewHolder::class.java)
    private val mockSchoolList = mock(ArrayList<School>()::class.java)
    private val mockListener = mock(SchoolListItemClickListener::class.java)
    private lateinit var adapter: SchoolRecyclerViewAdapter

    @Before
    fun setUp() {
        adapter = SchoolRecyclerViewAdapter(mockSchoolList, mockListener)
        loadSchoolList()
    }

    @Test
    fun `test the school list adapter item count`() {
        every { mockSchoolListAdapter.setSchoolList(loadSchoolList()) } just Runs
        every { mockSchoolListAdapter.itemCount } returns loadSchoolList().size
        mockSchoolListAdapter.setSchoolList(loadSchoolList())
        assertThat(mockSchoolListAdapter.itemCount).isEqualTo(loadSchoolList().size)
        verify { mockSchoolListAdapter.setSchoolList(loadSchoolList()) }
        verify { mockSchoolListAdapter.itemCount }
    }

    @Test
    fun `test if adapter and view holder is not null as mockk`() {
        assertThat(mockSchoolListAdapter).isNotNull()
        assertThat(mockSchoolListAdapterHolder).isNotNull()
    }

    @Test
    fun `test if adapter and view holder is not null as mockito mock`() {
        val mockAdapter = mock(SchoolRecyclerViewAdapter::class.java)
        assertThat(mockAdapter).isNotNull()
        assertThat(mockSchoolListAdapterHolder).isNotNull()
    }

    @Test
    fun `test if view holder is bind properly`() {
        val mockHolder = createViewHolder()
        adapter.schoolList = loadSchoolList()
        adapter.onBindViewHolder(mockHolder, 0)
        mockHolder.bind(loadSchoolList()[0])
        mockHolder.binding.schoolName.text = loadSchoolList()[0].school_name
        mockHolder.binding.schoolAddress.text = loadSchoolList()[0].primary_address_line_1

        assertThat(mockHolder.binding.schoolName.visibility).isEqualTo(View.VISIBLE)
        assertThat(mockHolder.binding.schoolName.text).isEqualTo("Clinton School Writers & Artists, M.S. 260")

        assertThat(mockHolder.binding.schoolAddress.visibility).isEqualTo(View.VISIBLE)
        assertThat(mockHolder.binding.schoolAddress.text).isEqualTo("10 East 15th Street")

        assertThat(mockHolder.binding.schoolListItemCard.performClick())
    }

    private fun createViewHolder(): SchoolRecyclerViewAdapter.SchoolViewHolder {
        val linearLayout = LinearLayout(getApplicationContext())
        return adapter.onCreateViewHolder(linearLayout, 0)
    }

    private fun loadSchoolList(): MutableList<School> {
        val schoolList = mutableListOf<School>()
        val schools =
            getObjectList(getJsonAsString(fileName = "schools_list.json"), School::class.java)
        schoolList.addAll(schools)
        return schoolList
    }
}