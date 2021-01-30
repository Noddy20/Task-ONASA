package com.onasa.pictures.uiModules.home.main

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.onasa.pictures.R
import com.onasa.pictures.databinding.FragmentHomeBinding
import com.onasa.pictures.models.BaseResponse
import com.onasa.pictures.models.ResponseStatus
import com.onasa.pictures.models.data.ModelPicture
import com.onasa.pictures.uiModules.base.BaseFragment
import com.onasa.pictures.uiModules.home.ViewModelHome
import com.onasa.pictures.uiModules.home.details.FragmentPictureDetail
import com.onasa.pictures.utils.extFunctions.hideAndStop
import dagger.hilt.android.AndroidEntryPoint
import com.onasa.pictures.utils.extFunctions.invoke
import com.onasa.pictures.utils.extFunctions.showAndStart
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class FragmentHome : BaseFragment<FragmentHomeBinding>() {

    override val layoutId = R.layout.fragment_home

    private val mViewModel by activityViewModels<ViewModelHome>()

    @Inject
    lateinit var mAdapter: AdapterPicturesList

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDataBinding{

            rvHomePictures.adapter = mAdapter
            mAdapter.itemClickListener = { model, pos ->
                findNavController().navigate(R.id.navActionHomeToDetail, bundleOf(
                    FragmentPictureDetail.EXTRA_KEY_MODEL_PICTURE to model,
                    FragmentPictureDetail.EXTRA_KEY_CURRENT_PICTURE_POSITION to pos
                ))
            }

            swipeRefreshLayout.setOnRefreshListener {
                mViewModel{
                    //erase stored json and reload
                    modelsJsonStr = null
                    mAdapter.submitList(null)
                    setInitialCallData()
                }
            }

            mViewModel.pictureModelsLD.observe(viewLifecycleOwner){
                updateUi(it)
            }
            if (mViewModel.pictureModelsLD.value == null) mViewModel.setInitialCallData()

        }
    }

    private fun FragmentHomeBinding.updateUi(response: BaseResponse<List<ModelPicture>?>){
        Timber.d("updateUi ${response.status}")
        when(response.status){
            ResponseStatus.LOADING -> {
                includeEmptyVew{
                    rootEmptyView.isVisible = false
                    lottieAnim.pauseAnimation()
                }
                shimmerView.showAndStart()
            }
            ResponseStatus.SUCCESS -> {
                includeEmptyVew{
                    rootEmptyView.isVisible = false
                    lottieAnim.pauseAnimation()
                }
                swipeRefreshLayout.isRefreshing = false
                shimmerView.hideAndStop()
                mAdapter.submitList(response.data)
            }
            ResponseStatus.ERROR -> {
                shimmerView.hideAndStop()
                swipeRefreshLayout.isRefreshing = false
                includeEmptyVew{
                    rootEmptyView.isVisible = true
                    lottieAnim.playAnimation()
                }
            }
        }
    }

}