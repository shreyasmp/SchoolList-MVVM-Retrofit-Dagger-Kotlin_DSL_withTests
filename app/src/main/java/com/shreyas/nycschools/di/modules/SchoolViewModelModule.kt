package com.shreyas.nycschools.di.modules

import androidx.lifecycle.ViewModel
import com.shreyas.nycschools.di.annotations.ViewModelKey
import com.shreyas.nycschools.viewmodel.SchoolDetailsViewModel
import com.shreyas.nycschools.viewmodel.SchoolListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SchoolViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SchoolListViewModel::class)
    abstract fun bindSchoolListViewModel(schoolListViewModel: SchoolListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SchoolDetailsViewModel::class)
    abstract fun bindSchoolDetailsViewModel(schoolDetailsViewModel: SchoolDetailsViewModel): ViewModel
}