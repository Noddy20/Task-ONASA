package com.onasa.pictures.uiModules.home

import android.content.Context
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.liveData
import com.onasa.pictures.R
import com.onasa.pictures.models.BaseResponse
import com.onasa.pictures.models.data.ModelPicture
import kotlinx.coroutines.delay
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RepoHome @Inject constructor(private val mAppContext: Context) {

    fun getModelsFromAssets(modelsJsonStr: String?, fileName: String, corContext: CoroutineContext,
                            onSuccess: (String) -> Unit) =
        liveData(corContext) {
            kotlin.runCatching {
                //emit loading status
                emit(BaseResponse.loading<List<ModelPicture>?>(mAppContext.getString(R.string.msg_no_data_loading)))
                if (modelsJsonStr.isNullOrBlank()) delay(2000)     //delay to show loading UI
                modelsJsonStr?:mAppContext.assets.open(fileName).bufferedReader().readText().let { jsonStr ->
                    //Parse Json to models list
                    val list = parsePicturesJson(jsonStr)
                    Timber.d("getModelsFromAssets List -> $list")
                    if (list.isNullOrEmpty()) emit(BaseResponse.error<List<ModelPicture>?>(mAppContext.getString(R.string.msg_no_data_found)))
                    else {
                        //emit data
                        onSuccess(jsonStr)
                        emit(BaseResponse.success(list, mAppContext.getString(R.string.msg_success)))
                    }
                }
            }.onFailure {
                Timber.e(it, "getModelsFromAssets Exc : ")
                //emit error
                emit(BaseResponse.error<List<ModelPicture>?>(mAppContext.getString(R.string.msg_something_went_wrong)))
            }
        }


    fun parsePicturesJson(jsonStr: String): List<ModelPicture>?{
        kotlin.runCatching {
            return Json.decodeFromString(jsonStr)
        }.onFailure {  }
        return null
    }

}