package com.shreyas.nycschools.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.shreyas.nycschools.R
import com.shreyas.nycschools.databinding.SchoolListItemBinding
import com.shreyas.nycschools.model.School
import com.shreyas.nycschools.view.callback.SchoolListItemClickListener

open class SchoolRecyclerViewAdapter() :
    RecyclerView.Adapter<SchoolRecyclerViewAdapter.SchoolViewHolder>() {

    @VisibleForTesting
    internal lateinit var schoolList: List<School>

    private lateinit var schoolListItemClickListener: SchoolListItemClickListener

    constructor(
        listOfSchools: MutableList<School>,
        clickListener: SchoolListItemClickListener
    ) : this() {
        this.schoolList = listOfSchools
        schoolListItemClickListener = clickListener
    }

    inner class SchoolViewHolder(val binding: SchoolListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(school: School) {
            binding.school = school
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        val binding: SchoolListItemBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context), R.layout.school_list_item,
                parent, false
            )
        return SchoolViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        holder.bind(schoolList[position])
        holder.binding.apply {
            schoolListItemCard.setOnClickListener {
                schoolListItemClickListener.onClick(schoolList[position])
            }
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int = schoolList.size

    fun setSchoolList(listOfSchool: List<School>) {
        schoolList = listOfSchool
        notifyDataSetChanged()
    }
}