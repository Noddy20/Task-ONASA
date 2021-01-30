package com.onasa.pictures.di.modules.fragmentModules

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.onasa.pictures.uiModules.home.details.AdapterPicturesDetailed
import com.onasa.pictures.uiModules.home.main.AdapterPicturesList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object FragmentAdaptersModule {

    @Provides
    @FragmentScoped
    fun providePicturesListAdapter(mFragment: Fragment) = AdapterPicturesList(mFragment.lifecycleScope)

    @Provides
    @FragmentScoped
    fun providePicturesDetailedAdapter() = AdapterPicturesDetailed()

}