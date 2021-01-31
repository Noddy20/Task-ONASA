package com.onasa.pictures.uiModules.home

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.onasa.pictures.R
import com.onasa.pictures.models.BaseResponse
import com.onasa.pictures.models.data.ModelPicture
import com.onasa.pictures.uiModules.base.BaseViewModel
import com.onasa.pictures.utils.delegates.getData
import com.onasa.pictures.utils.delegates.getDataNullable
import com.onasa.pictures.utils.extFunctions.IO
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import timber.log.Timber

class ViewModelHome @ViewModelInject constructor(
    private val mRepoHome: RepoHome,
    @Assisted savedStateHandle: SavedStateHandle
): BaseViewModel() {

    companion object{
        private const val JSON_DATA_FILE_NAME = "data.json"
    }

    //To save and restore Picture Details Dialog
    var isDetailsShowing by savedStateHandle.getData(false)

    //To save json string in savedStateHandle and reuse
    var modelsJsonStr by savedStateHandle.getDataNullable<String?>(null)

    //Picture Models List LiveData Observable
    val pictureModelsLD = mInitialCallsMLD.switchMap {
        mRepoHome.getModelsFromAssets(modelsJsonStr, JSON_DATA_FILE_NAME,viewModelIoCorContext){ jsonStr->
            //save json string in savedStateHandle for reuse
            if (modelsJsonStr.isNullOrBlank()) modelsJsonStr = jsonStr
        }
    }

}