package com.mary.happybirthday.presentation.detail_screen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.mary.happybirthday.R
import com.mary.happybirthday.presentation.base.BaseBottomSheet
import kotlinx.android.synthetic.main.image_picker_dialog.*

class ImagePickerBottomSheet : BaseBottomSheet() {

    override val viewResource = R.layout.image_picker_dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        picker_gallery_btn.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                DetailFragment.CAMERA_CHOSEN, false
            )
        }

        picker_camera_btn.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                DetailFragment.CAMERA_CHOSEN, true
            )
        }
    }
}