package com.onasa.pictures.utils.extFunctions

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import com.onasa.pictures.models.sealedModels.SealedNetState
import timber.log.Timber

/**
 *   Network Connectivity Functions
 */

fun Context.getNetState(connMan: ConnectivityManager? = null): SealedNetState {
    kotlin.runCatching {
        (connMan ?: applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?)?.let { conMan ->
            val capabilities = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                conMan.getNetworkCapabilities(conMan.activeNetwork)
            } else {
                @Suppress("DEPRECATION")
                conMan.activeNetworkInfo
            }
            if (capabilities != null) {
                return SealedNetState.Available
            }
        }
    }.onFailure {
        Timber.e(it, "Exc isNetConnected : ")
    }
    return SealedNetState.NotAvailable.UnAvailable
}

fun Context.isNetConnected(connMan: ConnectivityManager? = null): Boolean {
    return (getNetState(connMan) == SealedNetState.Available)
}