package com.onasa.pictures.models.other

import androidx.annotation.ColorRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import com.onasa.pictures.R
import com.onasa.pictures.models.BaseModel

data class ModelSnackbarData(
        var msg: String,
        var coordinatorLayout: CoordinatorLayout,
        @ColorRes var bgColor: Int = R.color.colorBgSnackBar,
        @ColorRes var txtColor: Int = R.color.colorTextWhite,
        var length: Int = Snackbar.LENGTH_SHORT,
        var action: String? = null
) : BaseModel
