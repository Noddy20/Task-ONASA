package com.onasa.pictures.models

import androidx.annotation.Keep

@Keep
data class BaseResponse<out T>(val status: ResponseStatus, val data: T?, val message: String?) {

    companion object {

        fun <T> error(msg: String?): BaseResponse<T> {
            return BaseResponse(ResponseStatus.ERROR, null, msg)
        }

        fun <T> loading(msg: String?): BaseResponse<T> {
            return BaseResponse(ResponseStatus.LOADING, null, msg)
        }

        fun <T> success(data: T?, msg: String?): BaseResponse<T> {
            return BaseResponse(ResponseStatus.SUCCESS, data, msg)
        }

    }

}

enum class ResponseStatus {
    ERROR, LOADING, SUCCESS
}