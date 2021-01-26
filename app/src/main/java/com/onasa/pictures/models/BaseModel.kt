package com.onasa.pictures.models

import androidx.recyclerview.widget.DiffUtil

interface BaseModel

class BaseItemDiffCallback<T>(private val areContentsTheSame: (oldItem: T, newItem: T) -> Boolean) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = areContentsTheSame(oldItem, newItem)

}