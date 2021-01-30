package com.onasa.pictures.uiModules.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.onasa.pictures.R
import com.onasa.pictures.databinding.ActivityHomeBinding
import com.onasa.pictures.models.sealedModels.SealedNetState
import com.onasa.pictures.uiModules.base.BaseAppCompatActivity
import com.onasa.pictures.utils.extFunctions.showNetworkStateSnackBar
import dagger.hilt.android.AndroidEntryPoint
import com.onasa.pictures.utils.extFunctions.invoke
import com.onasa.pictures.utils.extFunctions.setupToolbar

@AndroidEntryPoint
class ActivityHome : BaseAppCompatActivity() {

    private val mDataBinding by binding<ActivityHomeBinding>(R.layout.activity_home)
    private val mViewModel by viewModels<ViewModelHome>()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding{

            navController = (supportFragmentManager.findFragmentById(R.id.navFragmentContainer) as NavHostFragment).navController
            navController.setGraph(R.navigation.nav_graph_home)

            setupToolbar(includeAppBar.includeToolbar, navController = navController)

        }
    }

    override fun networkStatChanged(netState: SealedNetState) {
        showNetworkStateSnackBar(netState, mDataBinding.actRootView)
    }

}