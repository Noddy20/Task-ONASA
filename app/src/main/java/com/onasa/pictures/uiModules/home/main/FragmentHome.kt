package com.onasa.pictures.uiModules.home.main

import android.os.Bundle
import android.view.View
import com.onasa.pictures.R
import com.onasa.pictures.databinding.FragmentHomeBinding
import com.onasa.pictures.uiModules.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import com.onasa.pictures.utils.extFunctions.invoke

@AndroidEntryPoint
class FragmentHome : BaseFragment<FragmentHomeBinding>() {

    override val layoutId = R.layout.fragment_home



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDataBinding{
            shimmerView.startShimmer()
        }
    }

}