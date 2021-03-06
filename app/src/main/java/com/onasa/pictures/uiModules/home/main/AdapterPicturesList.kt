package com.onasa.pictures.uiModules.home.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.ListAdapter
import com.onasa.pictures.R
import com.onasa.pictures.databinding.ItemPictureListingLayoutBinding
import com.onasa.pictures.models.data.ModelPicture
import com.onasa.pictures.uiModules.base.BaseViewHolder
import com.onasa.pictures.utils.extFunctions.extCoroutineViewBind.launchViewClick
import com.onasa.pictures.utils.extFunctions.extCoroutineViewBind.launchViewClicks
import com.onasa.pictures.utils.extFunctions.extCoroutineViewBind.views
import javax.inject.Inject
import com.onasa.pictures.utils.extFunctions.invoke
import com.onasa.pictures.utils.extFunctions.setTint

class AdapterPicturesList @Inject constructor(private val mLifecycleScope: LifecycleCoroutineScope) :
    ListAdapter<ModelPicture, AdapterPicturesList.MyViewHolder>(ModelPicture.DIFF_UTIL_CALLBACK) {

    var itemClickListener: ((ModelPicture, Boolean, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(ItemPictureListingLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class MyViewHolder(mDataBinding: ItemPictureListingLayoutBinding): BaseViewHolder<ItemPictureListingLayoutBinding>(mDataBinding) {

        init {
            mDataBinding{
                views(btnBookmark, itemRootView).launchViewClicks(mLifecycleScope){
                    itemClickListener?.invoke(getItem(adapterPosition), (it.id == R.id.btnBookmark), adapterPosition)
                }
            }
        }

        override fun bind(pos: Int) {
            mDataBinding{
                bModel = getItem(pos)
                if (bModel.isBookmarked) btnBookmark.setTint(Color.RED) else btnBookmark.imageTintList = null
            }
            super.bind(pos)
        }


    }

}