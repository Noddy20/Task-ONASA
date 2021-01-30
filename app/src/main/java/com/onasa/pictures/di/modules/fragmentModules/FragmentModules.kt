package com.onasa.pictures.di.modules.fragmentModules

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.onasa.pictures.uiModules.dialogs.MyDialogs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object FragmentModules {

    @Provides
    @FragmentScoped
    fun provideMyDialogsToFragment(mActivity: Activity, mFragment: Fragment) = MyDialogs(mActivity, mFragment.lifecycleScope)

}