package com.mary.happybirthday.presentation.base

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mary.happybirthday.presentation.image_picker_screen.ImagePickType
import com.mary.happybirthday.presentation.image_picker_screen.ImagePickerBottomSheet.Companion.CAMERA_CHOSEN

abstract class BaseFragment : Fragment() {

    protected abstract val viewResource: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(viewResource, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()

        findNavController().currentBackStackEntry?.savedStateHandle?.
            getLiveData<ImagePickType>(CAMERA_CHOSEN)?.observe(viewLifecycleOwner, Observer { type ->
            if(type == ImagePickType.GALLERY || type == ImagePickType.CAMERA) {
                when(type) {
                    ImagePickType.CAMERA -> openCamera()
                    ImagePickType.GALLERY -> openGallery()
                    else -> { /* Do nothing */ }
                }
                findNavController().currentBackStackEntry?.savedStateHandle?.set(
                    CAMERA_CHOSEN, ImagePickType.UNDEFINED)
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == REQUEST_CODE_WRITE_STORAGE
            && grantResults.isNotEmpty()
            && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
            actionOnStoragePermissionGranted()
        }
    }

    protected abstract fun initUI()

    protected abstract fun actionOnStoragePermissionGranted()

    protected fun checkPermissionAndExecute(action: () -> Unit) {
        if(ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_WRITE_STORAGE
            )
        else action()
    }

    private fun openGallery() {
        startActivityForResult(
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
            REQUEST_CODE_GALLERY
        )
    }

    protected abstract fun openCamera()

    protected companion object {
        const val REQUEST_CODE_WRITE_STORAGE = 111
        const val REQUEST_CODE_GALLERY = 4321
        const val REQUEST_CODE_CAMERA = 1234
    }
}
