package com.shreyas.nycschools.di.modules

import com.shreyas.nycschools.di.annotations.ActivityScope
import com.shreyas.nycschools.view.MainActivity
import com.shreyas.nycschools.view.SchoolDetailsFragment
import com.shreyas.nycschools.view.SchoolListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SchoolViewModule {

    // Activities here
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity

    // Fragment here
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributesSchoolListFragment(): SchoolListFragment

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributesSchoolDetailFragment(): SchoolDetailsFragment
}