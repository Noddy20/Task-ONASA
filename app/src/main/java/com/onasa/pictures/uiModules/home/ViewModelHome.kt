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
import com.onasa.pictures.utils.delegates.getDataNullable
import com.onasa.pictures.utils.extFunctions.IO
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import timber.log.Timber

class ViewModelHome @ViewModelInject constructor(
    @ApplicationContext private val mAppContext: Context,
    @Assisted savedStateHandle: SavedStateHandle
): BaseViewModel() {

    companion object{
        private const val JSON_DATA_FILE_NAME = "data.json"
    }

    //To save json string in savedStateHandle and reuse
    var modelsJsonStr by savedStateHandle.getDataNullable<String?>(null)

    //Picture Models List LiveData Observable
    val pictureModelsLD = mInitialCallsMLD.switchMap { getModelsFromAssets() }

    private fun getModelsFromAssets() = liveData(viewModelScope.coroutineContext.IO) {
        kotlin.runCatching {
            //emit loading status
            emit(BaseResponse.loading<List<ModelPicture>?>(mAppContext.getString(R.string.msg_no_data_loading)))
            delay(2000)     //delay to show loading UI
            modelsJsonStr?:mAppContext.assets.open(JSON_DATA_FILE_NAME).bufferedReader().readText().let { jsonStr ->
                //Parse Json to models list
                val list = Json.decodeFromString<List<ModelPicture>?>(jsonStr)
                //save json string in savedStateHandle for reuse
                if (modelsJsonStr.isNullOrBlank()) modelsJsonStr = jsonStr
                Timber.d("getModelsFromAssets List -> $list")
                //emit data
                emit(BaseResponse.success(list, mAppContext.getString(R.string.msg_success)))
            }
        }.onFailure {
            Timber.e(it, "getModelsFromAssets Exc : ")
            //emit error
            emit(BaseResponse.error<List<ModelPicture>?>(mAppContext.getString(R.string.msg_no_data_found)))
        }
    }

}