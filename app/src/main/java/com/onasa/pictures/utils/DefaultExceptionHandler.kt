package com.onasa.pictures.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.system.exitProcess
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber

class DefaultExceptionHandler @Inject constructor(@ApplicationContext private val appContext: Context) : Thread.UncaughtExceptionHandler {

    companion object {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            Timber.e(exception, "Root CoroutineExceptionHandler : ")
        }
    }

    private var onExceptionCaught: ((t: Thread, e: Throwable) -> Unit)? = null
    fun setOnExceptionCaughtListener(listener: (t: Thread, e: Throwable) -> Unit) {
        onExceptionCaught = listener
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        onExceptionCaught?.invoke(t, e)

        Timber.e(e, "PrintStackTrace: ")

        /**
         *     Here we can use our custom error report system or we can also show a Dialog to user, to submit crash info using appContext + AlarmManager + A Activity with Dialog code
         */

        exitProcess(2)
    }
}