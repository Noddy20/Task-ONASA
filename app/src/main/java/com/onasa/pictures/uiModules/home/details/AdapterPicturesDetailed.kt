package com.onasa.pictures.uiModules.home.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.onasa.pictures.databinding.ItemPictureDetailLayoutBinding
import com.onasa.pictures.models.data.ModelPicture
import com.onasa.pictures.uiModules.base.BaseViewHolder
import com.onasa.pictures.utils.extFunctions.invoke
import javax.inject.Inject

class AdapterPicturesDetailed @Inject constructor() :
    ListAdapter<ModelPicture, AdapterPicturesDetailed.MyViewHolder>(ModelPicture.DIFF_UTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(ItemPictureDetailLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class MyViewHolder(mDataBinding: ItemPictureDetailLayoutBinding): BaseViewHolder<ItemPictureDetailLayoutBinding>(mDataBinding) {

        override fun bind(pos: Int) {
            mDataBinding{
                bModel = getItem(pos)
            }
            super.bind(pos)
        }

    }

}