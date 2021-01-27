package com.onasa.pictures.utils.extFunctions

import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.onasa.pictures.databinding.BarToolbarLayoutBinding
import com.onasa.pictures.uiModules.base.BaseAppCompatActivity

fun AppCompatActivity.setFullScreen() {
    window.apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setDecorFitsSystemWindows(false)
            insetsController?.let { isCont ->
                isCont.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                isCont.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }
}

fun AppCompatActivity.exitFullScreen() {
    window.apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setDecorFitsSystemWindows(true)
            insetsController?.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        } else {
            @Suppress("DEPRECATION")
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        }
    }
}

fun AppCompatActivity.isActivityNotFullScreen(): Boolean {
    @Suppress("DEPRECATION")
    return ((window.decorView.visibility and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0)
}

/**
 *    Toolbar Setup
 */

fun BaseAppCompatActivity.setupToolbar(toolbarBinding: BarToolbarLayoutBinding, navUpEnabled: Boolean = true, navController: NavController? = null){
    //Setup as ActionBar
    setSupportActionBar(toolbarBinding.toolbar)
    supportActionBar?.apply {
        if (navUpEnabled) {
            setDisplayShowHomeEnabled(true) // show or hide the default home button
            setDisplayHomeAsUpEnabled(true)
        }
    }

    //Set NavController if any
    navController?.let { nav->
        setupActionBarWithNavController(this, nav)
    }
}