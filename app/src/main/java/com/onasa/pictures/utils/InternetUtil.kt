package com.onasa.pictures.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.onasa.pictures.models.sealedModels.SealedNetState
import com.onasa.pictures.utils.extFunctions.getNetState
import com.onasa.pictures.utils.extFunctions.isNetConnected
import com.onasa.pictures.utils.extFunctions.launchWithExcHandler
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class InternetUtil @Inject constructor(@ApplicationContext private val appContext: Context) : LiveData<SealedNetState>() {

    companion object {
        var CURRENT_NET_STATE: SealedNetState = SealedNetState.NotAvailable.UnAvailable
    }

    private val connMan = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    private var lastStateAvailability = true

    val isNetConnected: Boolean
        get() {
            return appContext.isNetConnected(connMan)
        }

    val netState: SealedNetState
        get() {
            return appContext.getNetState(connMan)
        }

    init {
        CURRENT_NET_STATE = netState
    }

    private val networkStateObject = object : ConnectivityManager.NetworkCallback() {

        override fun onLost(network: Network) {
            postState(SealedNetState.NotAvailable.Lost)
            super.onLost(network)
        }

        override fun onUnavailable() {
            postState(SealedNetState.NotAvailable.UnAvailable)
            super.onUnavailable()
        }

        override fun onAvailable(network: Network) {
            postState(SealedNetState.Available)
            super.onAvailable(network)
        }

        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            if (lastStateAvailability) {
                ProcessLifecycleOwner.get().lifecycleScope.launchWithExcHandler {
                    withContext(Dispatchers.IO) {
                        delay(10)
                        postValue(SealedNetState.CapabilitiesChanged(networkCapabilities))
                        lastStateAvailability = false
                    }
                }
            }
            super.onCapabilitiesChanged(network, networkCapabilities)
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            if (lastStateAvailability) {
                ProcessLifecycleOwner.get().lifecycleScope.launchWithExcHandler {
                    withContext(Dispatchers.IO) {
                        delay(10)
                        postValue(SealedNetState.Loosing(maxMsToLive))
                        lastStateAvailability = false
                    }
                }
            }
            super.onLosing(network, maxMsToLive)
        }

        private fun postState(state: SealedNetState) {
            Timber.d("postState $state ${state != CURRENT_NET_STATE} ${hasActiveObservers()}")
            if (hasActiveObservers() && state != CURRENT_NET_STATE) {
                CURRENT_NET_STATE = state
                postValue(CURRENT_NET_STATE)
                lastStateAvailability = true
            }
        }
    }

    fun registerObserver() {
        if (!hasActiveObservers()) {
            connMan?.registerNetworkCallback(networkRequestBuilder(), networkStateObject)
        }
    }

    fun unregisterObserver() {
        connMan?.unregisterNetworkCallback(networkStateObject)
    }

    private fun networkRequestBuilder(): NetworkRequest {
        return NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
    }
}
