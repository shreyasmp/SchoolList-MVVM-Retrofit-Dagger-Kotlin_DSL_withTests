package com.shreyas.nycschools.di

import android.app.Application
import android.content.Context
import com.shreyas.nycschools.di.modules.SchoolViewModelModule
import com.shreyas.nycschools.di.modules.SchoolViewModule
import com.shreyas.nycschools.di.modules.ServiceModule
import com.shreyas.nycschools.di.modules.ViewModelFactoryModule
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule

@Module(
    includes = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ViewModelFactoryModule::class,
        ServiceModule::class,
        SchoolViewModule::class,
        SchoolViewModelModule::class
    ]
)
abstract class SchoolModule {

    companion object {
        @Provides
        fun provideApplicationContext(application: Application): Context {
            return application.applicationContext
        }
    }
}