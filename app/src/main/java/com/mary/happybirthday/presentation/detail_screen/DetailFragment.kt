package com.mary.happybirthday.presentation.detail_screen

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.mary.happybirthday.R
import com.mary.happybirthday.presentation.base.BaseFragment
import com.mary.happybirthday.presentation.image_picker_screen.ImagePickerBottomSheet.Companion.CAMERA_CHOSEN
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_fragment.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class DetailFragment : BaseFragment() {

    private val detailViewModel: DetailViewModel by lifecycleScope.viewModel(this)
    override val viewResource = R.layout.detail_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        detailViewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            if(state.name != detail_name_edit.text.toString())
                detail_name_edit.setText(state.name)
            detail_dob_edit.setText(state.birthday)
            state.picture?.let {
                Picasso.get().load(state.picture).into(detail_profile_iv)
            }?: detail_profile_iv.setImageResource(R.drawable.default_place_holder_green)
            detail_show_btn.isEnabled = state.canShowInfo
        })

        findNavController().currentBackStackEntry?.savedStateHandle?.
            getLiveData<Boolean>(CAMERA_CHOSEN)?.observe(viewLifecycleOwner, Observer { isCamera ->
            if(isCamera)
                openCamera()
            else openGallery()
        })
    }

    override fun initUI() {
        detail_name_edit.doOnTextChanged { text, _, _, _ ->
            detailViewModel.changeName(text.toString())
        }

        detail_profile_iv.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_imagePickerBottomSheet)
        }

        detail_dob_edit.setOnClickListener {
            MaterialDatePicker.Builder.datePicker()
                .setSelection(System.currentTimeMillis())
                .build()
                .apply {
                    addOnPositiveButtonClickListener { millis ->
                        detailViewModel.changeBirthday(millis)
                    }
                }
                .show(parentFragmentManager, PICKER_TAG)
        }

        detail_show_btn.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_birthdayFragment)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_CAMERA -> detailViewModel.changePhoto(data?.data?: Uri.EMPTY)
                REQUEST_CODE_GALLERY -> detailViewModel.changePhoto(data?.data?: Uri.EMPTY)
            }
        }
    }

    private fun openGallery() {
        startActivityForResult(
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
            REQUEST_CODE_GALLERY
        )
    }

    private fun openCamera() {
        startActivityForResult(
            Intent(MediaStore.ACTION_IMAGE_CAPTURE),
            REQUEST_CODE_CAMERA
        )
    }

    companion object {
        private const val PICKER_TAG = "com.mary.happybirthday.presentation.detail_screen.DetailFragment.MaterialDatePicker"
        private const val REQUEST_CODE_GALLERY = 4321
        private const val REQUEST_CODE_CAMERA = 1234
    }
}
