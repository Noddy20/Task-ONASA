package com.onasa.pictures.uiModules.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected val mInitialCallsLD = MutableLiveData<Array<out Pair<String, Any?>?>>()
    fun setInitialCallData(vararg params: Pair<String, Any?>) {
        mInitialCallsLD.value = params
    }

}