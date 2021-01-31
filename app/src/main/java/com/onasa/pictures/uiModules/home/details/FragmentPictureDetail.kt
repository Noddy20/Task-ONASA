package com.onasa.pictures.uiModules.home.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.onasa.pictures.R
import com.onasa.pictures.databinding.FragmentPictureDetailBinding
import com.onasa.pictures.models.data.ModelPicture
import com.onasa.pictures.uiModules.base.BaseFragment
import com.onasa.pictures.uiModules.home.ActivityHome
import com.onasa.pictures.uiModules.home.ViewModelHome
import com.onasa.pictures.utils.extFunctions.extCoroutineViewBind.onPageSelected
import dagger.hilt.android.AndroidEntryPoint

import com.onasa.pictures.utils.extFunctions.invoke
import javax.inject.Inject

@AndroidEntryPoint
class FragmentPictureDetail : BaseFragment<FragmentPictureDetailBinding>() {

    companion object{
        const val EXTRA_KEY_MODEL_PICTURE = "modelPicture"
        const val EXTRA_KEY_CURRENT_PICTURE_POSITION = "currPos"
    }

    override val layoutId = R.layout.fragment_picture_detail

    private val mViewModel by activityViewModels<ViewModelHome>()
    private val args: FragmentPictureDetailArgs by navArgs()

    @Inject
    lateinit var mAdapter: AdapterPicturesDetailed

    private var modelsList: List<ModelPicture>? = null

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDataBinding{
            setHasOptionsMenu(true)
            (mActivity as ActivityHome).supportActionBar?.title = args.modelPicture?.title

            vpPictures.adapter = mAdapter

            vpPictures.onPageSelected(lifecycleScope){
                tvPictureCount.text = "${it+1}/${modelsList?.size?:""}"
                (mActivity as ActivityHome).supportActionBar?.title = modelsList?.get(it)?.title
            }

            modelsList = mViewModel.pictureModelsLD.value?.data
            if (modelsList.isNullOrEmpty()){
                //Send to Home if Data null
                findNavController().popBackStack()
            }else{
                tvPictureCount.text = "${vpPictures.currentItem + 1}/${modelsList?.size}"
                mAdapter.submitList(modelsList)
                vpPictures.post {
                    vpPictures.setCurrentItem(args.currPos, false)
                }
            }

            if (mViewModel.isDetailsShowing) showPictureDetails()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_picture_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuItemInfo){
            showPictureDetails()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        //Save isShowingBottomSheet status
        mViewModel.isDetailsShowing = mDialogs.isShowingBottomSheet
        super.onDestroy()
    }

    private fun showPictureDetails(){
        kotlin.runCatching {
            modelsList?.get(mDataBinding.vpPictures.currentItem)?.let {
                mDialogs.showPictureDetailsDialog(it)
                //Save isShowingBottomSheet status
                mViewModel.isDetailsShowing = mDialogs.isShowingBottomSheet
            }
        }
    }

}