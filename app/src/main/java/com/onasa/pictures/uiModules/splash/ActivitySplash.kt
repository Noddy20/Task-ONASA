package com.onasa.pictures.uiModules.splash

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.onasa.pictures.R
import com.onasa.pictures.constants.AppConstants
import com.onasa.pictures.databinding.ActivitySplashBinding
import com.onasa.pictures.models.sealedModels.SealedNetState
import com.onasa.pictures.uiModules.base.BaseAppCompatActivity
import com.onasa.pictures.uiModules.gotoHomeActivity
import com.onasa.pictures.utils.extFunctions.extCoroutineViewBind.onTransitionEnd
import com.onasa.pictures.utils.extFunctions.setFullScreen
import com.onasa.pictures.utils.extFunctions.invoke
import com.onasa.pictures.utils.extFunctions.isActivityNotFullScreen
import com.onasa.pictures.utils.extFunctions.showNetworkStateSnackBar
import com.transitionseverywhere.Recolor
import com.transitionseverywhere.extra.Scale
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ActivitySplash : BaseAppCompatActivity() {

    private val mDataBinding by binding<ActivitySplashBinding>(R.layout.activity_splash)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding{
            setFullScreen()
            clAppNames.post {                                                                       //Wait until the layout is ready to animate
                splashTransition()
            }
        }
    }

    override fun onStart() {
        if (isActivityNotFullScreen()) {
            setFullScreen()
        }
        super.onStart()
    }

    /**
     *   To get Network State change updates (Test by turning net on/off)
     */
    override fun networkStatChanged(netState: SealedNetState) {
        showNetworkStateSnackBar(netState, mDataBinding.actRootView)
    }

    private fun ActivitySplashBinding.splashTransition(){
        lifecycleScope.launchWhenResumed {                                                          // This code will only execute if lifecycle state is onResumed
            TransitionSet().apply {
                duration = AppConstants.SPLASH_SCREEN_TRANSITION_DELAY
                addTransition(Fade().addTarget(clAppNames))
                addTransition(Scale().addTarget(clAppNames))
                addTransition(Recolor().addTarget(tvAppName))
            }.let { transitionSet ->
                transitionSet.onTransitionEnd(lifecycleScope){
                    Timber.d("onTransitionEnd")
                    gotoHomeActivity()
                    finish()
                }
                clAppNames.post {
                    TransitionManager.beginDelayedTransition(actRootView, transitionSet)
                    Timber.d("onTransition beginDelayedTransition ${lifecycle.currentState}")
                    clAppNames.isVisible = true
                    tvAppName.setTextColor(ContextCompat.getColor(mAppContext, R.color.colorAccent))
                }
            }
        }
    }


}