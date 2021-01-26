package com.onasa.pictures.utils.delegates

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun <reified T> SavedStateHandle.getData(initialValue: T): ReadWriteProperty<Any, T> = object : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return this@getData[property.name]?:initialValue
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        this@getData[property.name] = value
    }
}

inline fun <reified T> SavedStateHandle.getDataNullable(initialValue: T? = null): ReadWriteProperty<Any, T?> = object : ReadWriteProperty<Any, T?> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        return this@getDataNullable[property.name]?:initialValue
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        this@getDataNullable[property.name] = value
    }
}

inline fun <reified T> SavedStateHandle.getAsLiveData(initialValue: T? = null) =
    ReadOnlyProperty<Any, MutableLiveData<T>> { _, property ->
        if (initialValue == null) {
            this@getAsLiveData.getLiveData(property.name)
        } else {
            this@getAsLiveData.getLiveData(property.name, initialValue)
        }
    }