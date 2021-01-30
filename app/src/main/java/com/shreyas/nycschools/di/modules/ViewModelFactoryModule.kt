package com.shreyas.nycschools.di.modules

import androidx.lifecycle.ViewModelProvider
import com.shreyas.nycschools.di.DaggerViewModelFactory
import com.shreyas.nycschools.repository.INYCSchoolRepository
import com.shreyas.nycschools.repository.NYCSchoolRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bind(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @Singleton
    abstract fun providesNYCSchoolRepository(repository: NYCSchoolRepository): INYCSchoolRepository
}