package com.onasa.pictures.uiModules.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleCoroutineScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.onasa.pictures.R
import com.onasa.pictures.databinding.DialogBottomsheetPictureDetailsLayoutBinding
import com.onasa.pictures.models.data.ModelPicture
import com.onasa.pictures.utils.extFunctions.inflateBinding
import javax.inject.Inject

class MyDialogs @Inject constructor(val mActivity: Activity, val mLifecycleScope: LifecycleCoroutineScope) {

    var mDialog: Dialog? = null
    var mBottomSheetDialog: BottomSheetDialog? = null

    val isShowingDialog: Boolean
        get() {
            return isShowing(mDialog)
        }

    val isShowingBottomSheet: Boolean
        get() {
            return isShowing(mBottomSheetDialog)
        }

    private fun isShowing(dialog: Dialog?) = dialog?.isShowing ?: false

    private fun show(dialog: Dialog?) {
        dialog?.runCatching {
            if (!mActivity.isFinishing) show()
        }
    }

    fun showDialog(){
        show(mDialog)
    }

    fun showBottomSheet(){
        show(mBottomSheetDialog)
    }

    private fun dismiss(dialog: Dialog?) {
        dialog?.runCatching {
            if (isShowing) dismiss()
        }
    }

    fun dismiss() {
        dismiss(mDialog)
        mDialog = null
    }

    fun dismissBottomSheet() {
        dismiss(mBottomSheetDialog)
        mBottomSheetDialog = null
    }

    fun dismissAll() {
        dismiss()
        dismissBottomSheet()
    }


    /**
     *   Init Dialog
     */

    fun <T : ViewDataBinding> initNewDialog(@LayoutRes layout: Int): T {
        dismiss()

        val binding: T
        Dialog(mActivity).apply {
            window?.let { win ->
                win.requestFeature(Window.FEATURE_NO_TITLE)
                win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            binding = mActivity.layoutInflater.inflateBinding(layout, null)
            setContentView(binding.root)
        }.also { dialog ->
            mDialog = dialog
        }
        return binding
    }

    fun <T : ViewDataBinding> initNewBottomSheet(@LayoutRes layout: Int, @StyleRes styleRes: Int = R.style.AppTheme_BaseBottomSheetDialog): T {
        dismissBottomSheet()
        val binding: T
        BottomSheetDialog(mActivity, styleRes).apply {
            binding = mActivity.layoutInflater.inflateBinding(layout, null)
            setContentView(binding.root)
        }.also { dialog ->
            mBottomSheetDialog = dialog
        }
        return binding
    }

    /**
     *    App Dialogs
     */

    fun showPictureDetailsDialog(model: ModelPicture) =
        initNewBottomSheet<DialogBottomsheetPictureDetailsLayoutBinding>(R.layout.dialog_bottomsheet_picture_details_layout).apply {
            bModel = model
            showBottomSheet()
        }

}