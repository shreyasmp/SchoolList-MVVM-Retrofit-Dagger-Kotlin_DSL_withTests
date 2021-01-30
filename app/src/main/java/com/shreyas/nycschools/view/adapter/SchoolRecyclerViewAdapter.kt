package com.shreyas.nycschools.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.shreyas.nycschools.R
import com.shreyas.nycschools.databinding.SchoolListItemBinding
import com.shreyas.nycschools.model.School
import com.shreyas.nycschools.view.callback.SchoolListItemClickListener

class SchoolRecyclerViewAdapter() :
    RecyclerView.Adapter<SchoolRecyclerViewAdapter.SchoolViewHolder>() {

    companion object {
        private val TAG = SchoolRecyclerViewAdapter::class.java.simpleName
    }

    private lateinit var schoolList: List<School>
    lateinit var schoolListItemClickListener: SchoolListItemClickListener

    constructor(schoolList: List<School>, clickListener: SchoolListItemClickListener) : this() {
        this.schoolList = schoolList
        schoolListItemClickListener = clickListener
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
        holder.binding.schoolListItemCard.setOnClickListener {
            schoolListItemClickListener.onClick(schoolList[position])
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = schoolList.size

    inner class SchoolViewHolder(val binding: SchoolListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(school: School) {
            binding.school = school
            binding.executePendingBindings()
        }
    }
}