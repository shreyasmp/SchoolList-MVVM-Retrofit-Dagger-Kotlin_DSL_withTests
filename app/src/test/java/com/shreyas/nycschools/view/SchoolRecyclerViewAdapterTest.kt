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
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(SchoolRobolectricTestRunner::class)
class SchoolRecyclerViewAdapterTest {

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
        adapter.setSchoolList(loadSchoolList())
        assertThat(adapter.itemCount).isEqualTo(loadSchoolList().size)
    }

    @Test
    fun `test if adapter and view holder is not null as mockito mock`() {
        assertThat(createViewHolder()).isNotNull()
    }

    @Test
    fun `test if view holder is bind properly`() {
        val viewHolder = createViewHolder()
        adapter.schoolList = loadSchoolList()
        adapter.onBindViewHolder(viewHolder, 0)
        viewHolder.bind(loadSchoolList()[0])
        viewHolder.binding.schoolName.text = loadSchoolList()[0].school_name
        viewHolder.binding.schoolAddress.text = loadSchoolList()[0].primary_address_line_1

        assertThat(viewHolder.binding.schoolName.visibility).isEqualTo(View.VISIBLE)
        assertThat(viewHolder.binding.schoolName.text).isEqualTo("Clinton School Writers & Artists, M.S. 260")

        assertThat(viewHolder.binding.schoolAddress.visibility).isEqualTo(View.VISIBLE)
        assertThat(viewHolder.binding.schoolAddress.text).isEqualTo("10 East 15th Street")

        assertThat(viewHolder.binding.schoolListItemCard.performClick())
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