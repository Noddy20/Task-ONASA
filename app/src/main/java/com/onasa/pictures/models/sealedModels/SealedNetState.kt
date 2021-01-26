package com.onasa.pictures.models.sealedModels

import android.net.NetworkCapabilities

sealed class SealedNetState {

    object Available : SealedNetState()
    sealed class NotAvailable : SealedNetState() {
        object Lost : NotAvailable()
        object UnAvailable : NotAvailable()
    }
    data class Loosing(val maxMsToLive: Int) : SealedNetState()
    data class CapabilitiesChanged(val networkCapabilities: NetworkCapabilities) : SealedNetState()
}
