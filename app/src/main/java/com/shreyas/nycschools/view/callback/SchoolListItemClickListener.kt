package com.shreyas.nycschools.view.callback

import com.shreyas.nycschools.model.School

interface SchoolListItemClickListener {
    fun onClick(school: School)
}