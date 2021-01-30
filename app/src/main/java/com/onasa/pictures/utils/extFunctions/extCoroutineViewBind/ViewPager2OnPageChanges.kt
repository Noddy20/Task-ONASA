package com.onasa.pictures.utils.extFunctions.extCoroutineViewBind

import androidx.annotation.CheckResult
import androidx.viewpager2.widget.ViewPager2
import com.onasa.pictures.utils.extFunctions.launchWithExcHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive

fun ViewPager2?.onPageSelected(coroutineScope: CoroutineScope, drop: Int = 0, onPageSelected: (page: Int) -> Unit) {
    coroutineScope.launchWithExcHandler {
        this@onPageSelected?.onPageSelected()?.drop(drop)?.onEach { page ->
            onPageSelected(page)
        }?.launchIn(this)
    }
}

@CheckResult
private fun ViewPager2.onPageSelected(): Flow<Int> = channelFlow {
    val listener = viewPager2PageSelectionListener(this, ::offer)
    registerOnPageChangeCallback(listener)
    awaitClose { unregisterOnPageChangeCallback(listener) }
}

@CheckResult
private fun viewPager2PageSelectionListener(
    scope: CoroutineScope,
    emitter: (Int) -> Boolean
) = object : ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
        if (scope.isActive) { emitter(position) }
    }
}