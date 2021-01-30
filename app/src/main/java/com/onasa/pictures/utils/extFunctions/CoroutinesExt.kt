package com.onasa.pictures.utils.extFunctions

import com.onasa.pictures.utils.DefaultExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 *   Extensions for CoroutineContext + DefaultCoroutine Exception Handler
 */

fun CoroutineContext.withExcHandler(): CoroutineContext {
    return this + DefaultExceptionHandler.coroutineExceptionHandler
}

fun Dispatchers.io() = IO.withExcHandler()

fun Dispatchers.main() = Main.withExcHandler()

fun Dispatchers.mainImmediate() = Main.immediate.withExcHandler()

fun Dispatchers.default() = Default.withExcHandler()

fun Dispatchers.unconfined() = Unconfined.withExcHandler()

val CoroutineContext.IO : CoroutineContext
    get() = (this + Dispatchers.io())

/**
 *   launch Extension with DefaultCoroutine Exception Handler
 */

fun CoroutineScope.launchWithExcHandler(
    context: CoroutineContext = coroutineContext,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch(context.withExcHandler()) {
        block()
    }
}

// throttleFirst like RxJava Operator

fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> = flow {
    var lastEmissionTime = 0L
    collect { upstream ->
        val currentTime = System.currentTimeMillis()
        val mayEmit = currentTime - lastEmissionTime > windowDuration
        if (mayEmit) {
            lastEmissionTime = currentTime
            emit(upstream)
        }
    }
}

fun Job?.cancelIfActive() {
    if (this?.isActive == true) {
        cancel()
    }
}