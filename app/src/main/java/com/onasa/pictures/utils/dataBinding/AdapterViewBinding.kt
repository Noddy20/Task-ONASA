package com.onasa.pictures.utils.dataBinding

import android.view.View
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.onasa.pictures.utils.extFunctions.htmlText

object AdapterViewBinding {

    @JvmStatic
    @BindingAdapter(value = ["bind_isVisible"], requireAll = false)
    fun setViewVisibility(view: View, isVisible: Boolean) {
        view.isVisible = isVisible
    }

    @JvmStatic
    @BindingAdapter(value = ["bind_isInvisible"], requireAll = false)
    fun setViewBackgroundColor(view: View, isInvisible: Boolean) {
        view.isInvisible = isInvisible
    }

    @JvmStatic
    @BindingAdapter(value = ["bind_htmlText"], requireAll = true)
    fun setHtmlText(textView: TextView, str: String?) {
        textView.htmlText = str
    }

}