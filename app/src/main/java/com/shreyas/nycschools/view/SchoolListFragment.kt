package com.shreyas.nycschools.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.shreyas.nycschools.R
import com.shreyas.nycschools.base.BaseFragment
import com.shreyas.nycschools.databinding.FragmentSchoolListBinding
import com.shreyas.nycschools.model.School
import com.shreyas.nycschools.view.adapter.SchoolRecyclerViewAdapter
import com.shreyas.nycschools.view.callback.SchoolListItemClickListener
import com.shreyas.nycschools.viewmodel.SchoolListViewModel

/**
 *  School List Fragment
 */
class SchoolListFragment : BaseFragment<SchoolListViewModel>(), SchoolListItemClickListener {

    companion object {
        val TAG: String = SchoolListFragment::class.java.simpleName
    }

    private lateinit var binding: FragmentSchoolListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_school_list, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.fetchSchoolList()

        subscribeUI()

        return binding.root
    }

    private fun subscribeUI() {
        binding.isLoading = true
        viewModel.schoolList.observe(viewLifecycleOwner, { schoolList ->
            binding.isLoading = false
            binding.schoolList.adapter = SchoolRecyclerViewAdapter(schoolList, this)
            binding.schoolList.layoutManager = LinearLayoutManager(context)
        })
    }

    override fun getTitle(): String = getString(R.string.list_title_name)

    override fun onClick(school: School) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            Log.i(TAG, "Selected School: $school")
            (activity as MainActivity).showSchoolDetailFragment(school)
        }
    }
}