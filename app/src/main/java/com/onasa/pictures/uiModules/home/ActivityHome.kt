package com.onasa.pictures.uiModules.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.onasa.pictures.R
import com.onasa.pictures.databinding.ActivityHomeBinding
import com.onasa.pictures.models.sealedModels.SealedNetState
import com.onasa.pictures.uiModules.base.BaseAppCompatActivity
import com.onasa.pictures.utils.extFunctions.invoke
import com.onasa.pictures.utils.extFunctions.setupToolbar
import com.onasa.pictures.utils.extFunctions.showNetworkStateSnackBar
import dagger.hilt.android.AndroidEntryPoint

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.id == R.id.menuItemBookmarks){

        }
        return super.onOptionsItemSelected(item)
    }

}