package com.onasa.pictures.uiModules.splash

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.onasa.pictures.R
import com.onasa.pictures.databinding.ActivitySplashBinding
import com.onasa.pictures.models.sealedModels.SealedNetState
import com.onasa.pictures.uiModules.base.BaseAppCompatActivity
import com.onasa.pictures.utils.extFunctions.invoke
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
            lifecycleScope.launchWhenResumed {
                TransitionSet().apply {
                    duration = 3000L
                    addTransition(Fade())
                    addTransition(Scale())
                    addTransition(Recolor())
                }.addTarget(tvAppName).also { transitionSet ->
                    transitionSet.addListener(object : Transition.TransitionListener{
                        override fun onTransitionStart(transition: Transition) {
                            Timber.d("onTransitionStart")
                        }

                        override fun onTransitionEnd(transition: Transition) {
                            Timber.d("onTransitionEnd")
                        }

                        override fun onTransitionCancel(transition: Transition) {

                        }

                        override fun onTransitionPause(transition: Transition) {

                        }

                        override fun onTransitionResume(transition: Transition) {

                        }

                    })
                    TransitionManager.beginDelayedTransition(actRootView, transitionSet)
                    Timber.d("onTransition beginDelayedTransition")
                    tvAppName.isVisible = true
                    tvAppName.setTextColor(ContextCompat.getColor(mAppContext, R.color.colorAccent))
                }
            }
        }
    }

    override fun networkStatChanged(netState: SealedNetState) {

    }


}