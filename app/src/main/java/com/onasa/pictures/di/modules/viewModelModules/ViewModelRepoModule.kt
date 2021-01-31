package com.onasa.pictures.di.modules.viewModelModules

import android.content.Context
import com.onasa.pictures.uiModules.home.RepoHome
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object ViewModelRepoModule {

    @Provides
    @ActivityRetainedScoped
    fun provideHomeRepo(@ApplicationContext mAppContext: Context) = RepoHome(mAppContext)

}