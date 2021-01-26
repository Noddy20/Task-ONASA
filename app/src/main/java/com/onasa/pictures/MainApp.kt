package com.onasa.pictures

import android.app.Application
import com.onasa.pictures.utils.DefaultExceptionHandler
import com.onasa.pictures.utils.InternetUtil
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MainApp: Application() {

    @Inject
    lateinit var myExceptionHandler: DefaultExceptionHandler
    @Inject
    lateinit var mInternetUtil: InternetUtil
    @Inject
    lateinit var mLogUtilTree: Timber.DebugTree

    override fun onCreate() {
        super.onCreate()

        Timber.plant(mLogUtilTree)
        Thread.setDefaultUncaughtExceptionHandler(myExceptionHandler) // Init Uncaught Exception Handler

        // Init InternetUtil Observer
        mInternetUtil.registerObserver()

    }

}