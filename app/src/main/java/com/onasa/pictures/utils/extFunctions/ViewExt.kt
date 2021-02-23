package com.onasa.pictures.utils.extFunctions

import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.text.HtmlCompat
import androidx.core.text.toSpanned
import androidx.core.view.isVisible
import androidx.core.widget.ImageViewCompat
import com.facebook.shimmer.ShimmerFrameLayout

/**
 *   ShimmerFrameLayout Functions
 */

fun ShimmerFrameLayout.showAndStart() {
    startShimmer()
    isVisible = true
}

fun ShimmerFrameLayout.hideAndStop() {
    stopShimmer()
    isVisible = false
}


/**
 *  TextView Functions
 */

var TextView.htmlText: String?
    get() = HtmlCompat.toHtml((text ?: "").toSpanned(), HtmlCompat.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE)
    set(string) { text = HtmlCompat.fromHtml(string ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY) }


fun ImageView.setTint(@ColorInt color: Int){
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(color))
}


