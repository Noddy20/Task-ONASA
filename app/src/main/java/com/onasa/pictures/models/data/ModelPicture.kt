package com.onasa.pictures.models.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep
import android.os.Parcelable
import com.onasa.pictures.models.BaseItemDiffCallback
import com.onasa.pictures.models.BaseModel
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Serializable
data class ModelPicture(
    @SerialName("copyright") val copyright: String?,
    @SerialName("date") val date: String?,
    @SerialName("explanation") val explanation: String?,
    @SerialName("hdurl") val hdUrl: String?,
    @SerialName("media_type") val mediaType: String?,
    @SerialName("service_version") val serviceVersion: String?,
    @SerialName("title") val title: String?,
    @SerialName("url") val url: String?
) : Parcelable, BaseModel {

    companion object{

        val DIFF_UTIL_CALLBACK = BaseItemDiffCallback<ModelPicture> { oldItem, newItem->
            return@BaseItemDiffCallback oldItem.url == newItem.url
        }

    }

}