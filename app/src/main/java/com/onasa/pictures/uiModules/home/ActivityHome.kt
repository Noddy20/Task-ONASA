package com.onasa.pictures.uiModules.home

import android.os.Bundle
import com.onasa.pictures.R
import com.onasa.pictures.databinding.ActivityHomeBinding
import com.onasa.pictures.models.sealedModels.SealedNetState
import com.onasa.pictures.uiModules.base.BaseAppCompatActivity
import com.onasa.pictures.utils.extFunctions.showNetworkStateSnackBar
import dagger.hilt.android.AndroidEntryPoint
import com.onasa.pictures.utils.extFunctions.invoke

@AndroidEntryPoint
class ActivityHome : BaseAppCompatActivity() {

    private val mDataBinding by binding<ActivityHomeBinding>(R.layout.activity_home)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding{

        }
    }

    override fun networkStatChanged(netState: SealedNetState) {
        showNetworkStateSnackBar(netState, mDataBinding.actRootView)
    }

}