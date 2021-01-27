package com.onasa.pictures.uiModules

import android.content.Context
import android.content.Intent
import com.onasa.pictures.uiModules.home.ActivityHome

fun Context.gotoHomeActivity(){
    startActivity(Intent(this, ActivityHome::class.java))
}