package com.onasa.pictures.uiModules.home.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.ListAdapter
import com.onasa.pictures.databinding.ItemPictureListingLayoutBinding
import com.onasa.pictures.models.data.ModelPicture
import com.onasa.pictures.uiModules.base.BaseViewHolder
import com.onasa.pictures.utils.extFunctions.extCoroutineViewBind.launchViewClick
import javax.inject.Inject
import com.onasa.pictures.utils.extFunctions.invoke

class AdapterPicturesList @Inject constructor(private val mLifecycleScope: LifecycleCoroutineScope) :
    ListAdapter<ModelPicture, AdapterPicturesList.MyViewHolder>(ModelPicture.DIFF_UTIL_CALLBACK) {

    var itemClickListener: ((ModelPicture, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(ItemPictureListingLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class MyViewHolder(mDataBinding: ItemPictureListingLayoutBinding): BaseViewHolder<ItemPictureListingLayoutBinding>(mDataBinding) {

        init {
            mDataBinding.itemRootView.launchViewClick(mLifecycleScope){
                itemClickListener?.invoke(getItem(adapterPosition), adapterPosition)
            }
        }

        override fun bind(pos: Int) {
            mDataBinding{
                bModel = getItem(pos)
            }
            super.bind(pos)
        }


    }

}