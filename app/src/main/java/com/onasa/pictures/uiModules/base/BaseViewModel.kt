package com.onasa.pictures.uiModules.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onasa.pictures.utils.extFunctions.IO

abstract class BaseViewModel : ViewModel() {

    protected val viewModelIoCorContext by lazy { viewModelScope.coroutineContext.IO }

    protected val mInitialCallsMLD = MutableLiveData<Array<out Pair<String, Any?>?>>()
    fun setInitialCallData(vararg params: Pair<String, Any?>) {
        mInitialCallsMLD.value = params
    }

}