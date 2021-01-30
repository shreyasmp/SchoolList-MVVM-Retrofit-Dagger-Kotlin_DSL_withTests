package com.shreyas.nycschools.di

import android.app.Application
import com.shreyas.nycschools.MainApplication
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 *  Main Dagger component that creates the DI injection tree
 *  that includes the views, viewmodel's and service
 */
@Singleton
@Component(
    modules = [
        SchoolModule::class
    ]
)
interface AppComponent {

    fun inject(mainApplication: MainApplication)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}