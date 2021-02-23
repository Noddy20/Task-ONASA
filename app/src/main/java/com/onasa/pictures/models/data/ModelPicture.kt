package com.onasa.pictures.models.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep
import android.os.Parcelable
import com.onasa.pictures.models.BaseItemDiffCallback
import com.onasa.pictures.models.BaseModel
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Serializable
data class ModelPicture(
    @SerialName("copyright") val copyright: String? = null,
    @SerialName("date") val date: String? = null,
    @SerialName("explanation") val explanation: String? = null,
    @SerialName("hdurl") val hdUrl: String? = null,
    @SerialName("media_type") val mediaType: String? = null,
    @SerialName("service_version") val serviceVersion: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("url") val url: String? = null
) : Parcelable, BaseModel {

    @IgnoredOnParcel
    var isBookmarked = false

    companion object{

        val DIFF_UTIL_CALLBACK = BaseItemDiffCallback<ModelPicture> { oldItem, newItem->
            return@BaseItemDiffCallback oldItem.url == newItem.url
        }

    }

}