package com.shreyas.nycschools.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.shreyas.nycschools.R
import com.shreyas.nycschools.base.BaseFragment
import com.shreyas.nycschools.databinding.FragmentSchoolDetailsBinding
import com.shreyas.nycschools.model.School
import com.shreyas.nycschools.utils.Constants.SCHOOL_DETAILS
import com.shreyas.nycschools.viewmodel.SchoolDetailsViewModel

/**
 *  School Details Fragment
 */
class SchoolDetailsFragment : BaseFragment<SchoolDetailsViewModel>() {

    companion object {
        val TAG: String = SchoolDetailsFragment::class.java.simpleName
    }

    lateinit var binding: FragmentSchoolDetailsBinding

    private lateinit var school: School

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_school_details, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        school = arguments?.getSerializable(SCHOOL_DETAILS) as School

        // Set the school object to Mutable School object that can be used in XML
        viewModel.selectedSchool.value = school

        viewModel.fetchSchoolSATScores(school)

        return binding.root
    }

    override fun getTitle(): String = getString(R.string.details_title_name)
}