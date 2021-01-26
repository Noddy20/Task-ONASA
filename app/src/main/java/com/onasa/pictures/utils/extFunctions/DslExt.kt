package com.onasa.pictures.utils.extFunctions

/**
 *  DSL for invoke block
 */

operator fun <T> T.invoke(block: T.() -> Unit) = block()