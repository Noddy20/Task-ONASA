package com.onasa.pictures.uiModules.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected val mInitialCallsMLD = MutableLiveData<Array<out Pair<String, Any?>?>>()
    fun setInitialCallData(vararg params: Pair<String, Any?>) {
        mInitialCallsMLD.value = params
    }

}