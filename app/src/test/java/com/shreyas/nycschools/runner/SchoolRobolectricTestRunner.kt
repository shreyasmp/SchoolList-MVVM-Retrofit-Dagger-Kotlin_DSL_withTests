package com.shreyas.nycschools.runner

import org.junit.runners.model.InitializationError
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

class SchoolRobolectricTestRunner @Throws(InitializationError::class) constructor(testClass: Class<*>?) :
    RobolectricTestRunner(testClass) {

    override fun buildGlobalConfig(): Config {
        System.setProperty("unit.test", "true")
        return Config.Builder().setSdk(28).build()
    }
}