package com.mary.happybirthday.presentation.base

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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

        findNavController().currentBackStackEntry?.savedStateHandle?.let {
            it.getLiveData<ImagePickType>(CAMERA_CHOSEN).observe(viewLifecycleOwner, Observer { type ->
                if(type == ImagePickType.CAMERA || type == ImagePickType.GALLERY) {
                    it.set(CAMERA_CHOSEN, ImagePickType.UNDEFINED)
                    when (type) {
                        ImagePickType.CAMERA -> openCamera()
                        ImagePickType.GALLERY -> openGallery()
                        else -> { /* Do nothing */ }
                    }
                }
            })
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_CAMERA -> savePhoto(isCameraPhoto = true)
                REQUEST_CODE_GALLERY -> savePhoto(path = data?.data?: Uri.EMPTY)
            }
        }
    }

    protected abstract fun initUI()

    protected abstract fun actionOnStoragePermissionGranted()

    protected abstract fun openCamera()

    protected abstract fun savePhoto(path: Uri = Uri.EMPTY, isCameraPhoto: Boolean = false)

    private fun openGallery() {
        startActivityForResult(
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
            REQUEST_CODE_GALLERY
        )
    }

    protected fun openCamera(path: Uri) {
        startActivityForResult(
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, path)
            },
            REQUEST_CODE_CAMERA
        )
    }

    protected fun checkPermissionAndExecute(action: () -> Unit) {
        if(!isPermissionSet(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            || !isPermissionSet(Manifest.permission.CAMERA))
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                ),
                REQUEST_CODE_WRITE_STORAGE
            )
        else action()
    }

    private fun isPermissionSet(permission: String) : Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    protected companion object {
        const val REQUEST_CODE_WRITE_STORAGE = 111
        const val REQUEST_CODE_GALLERY = 4321
        const val REQUEST_CODE_CAMERA = 1234
    }
}
