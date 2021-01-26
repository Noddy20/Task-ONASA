package com.onasa.pictures.uiModules.base


import android.content.Context
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.onasa.pictures.models.sealedModels.SealedNetState
import com.onasa.pictures.utils.InternetUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

abstract class BaseAppCompatActivity : AppCompatActivity() {

    @Inject
    lateinit var mInternetUtil: InternetUtil

    @Inject
    @ApplicationContext
    lateinit var mAppContext: Context

    var registerNetReceiver: Boolean = true

    protected inline fun <reified T : ViewDataBinding> binding(@LayoutRes resId: Int): Lazy<T>
            = lazy { DataBindingUtil.setContentView<T>(this, resId).apply {
        lifecycleOwner = this@BaseAppCompatActivity
    }}

    override fun onResume() {
        super.onResume()
        if (registerNetReceiver) {
            mInternetUtil.observe(this, ::networkStatChanged)
        }
    }

    override fun onPause() {
        super.onPause()
        if (registerNetReceiver)
            unregisterNetReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (registerNetReceiver)
            unregisterNetReceiver()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    private fun unregisterNetReceiver() {
        kotlin.runCatching {
            mInternetUtil.removeObservers(this)
        }.onFailure { Timber.e(it, "unregisterNetReceiver : ") }
    }

    abstract fun networkStatChanged(netState: SealedNetState)

}
