package com.onasa.pictures.di.modules.appModules

import android.content.Context
import com.onasa.pictures.utils.DefaultExceptionHandler
import com.onasa.pictures.utils.InternetUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDefaultExceptionHandler(@ApplicationContext appContext: Context) = DefaultExceptionHandler(appContext)

    @Provides
    @Singleton
    fun provideInternetUtil(@ApplicationContext appContext: Context) = InternetUtil(appContext)

    @Provides
    @Singleton
    fun provideLogUtilTree() = Timber.DebugTree()                                                   // We can also use custom LogTree for Release Build

}