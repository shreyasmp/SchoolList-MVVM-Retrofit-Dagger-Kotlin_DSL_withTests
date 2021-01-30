package com.shreyas.nycschools

import android.app.Application
import com.shreyas.nycschools.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class MainApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Any>

    @Suppress("LeakingThis")
    var application: MainApplication = this

    override fun onCreate() {
        super.onCreate()

        createAppDaggerComponent()
    }

    open fun createAppDaggerComponent() {
        val appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = activityInjector
}