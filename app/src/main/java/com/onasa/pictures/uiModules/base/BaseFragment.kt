package com.onasa.pictures.uiModules.base


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.onasa.pictures.uiModules.dialogs.MyDialogs
import com.onasa.pictures.utils.extFunctions.inflateBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    @Inject
    @ApplicationContext
    lateinit var mAppContext: Context

    @Inject
    lateinit var mActivity: Activity

    @Inject
    lateinit var mDialogs: MyDialogs

    protected lateinit var mDataBinding: B
    protected abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDataBinding = inflater.inflateBinding(layoutId, container)
        mDataBinding.lifecycleOwner = viewLifecycleOwner
        return mDataBinding.root
    }

    override fun onDestroy() {
        mDialogs.dismissAll()
        super.onDestroy()
    }

}