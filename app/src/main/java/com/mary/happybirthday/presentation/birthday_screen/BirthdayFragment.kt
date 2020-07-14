package com.mary.happybirthday.presentation.birthday_screen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mary.happybirthday.R
import com.mary.happybirthday.presentation.base.BaseFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.birthday_fragment.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import kotlin.random.Random

class BirthdayFragment : BaseFragment() {

    private val birthdayViewModel: BirthdayViewModel by lifecycleScope.viewModel(this)
    override val viewResource = R.layout.birthday_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        birthdayViewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            birthday_header_tv.text = getString(R.string.birthday_title, state.name)
            birthday_footer_tv.text = resources.getQuantityString(
                if(state.timeUnits == TimeUnit.MONTH) R.plurals.birthday_months
                else R.plurals.birthday_years,
                state.age
            )
            birthday_age_iv.setImageResource(
                when(state.age) {
                    0 -> R.drawable.num_zero
                    1 -> R.drawable.num_one
                    2 -> R.drawable.num_two
                    3 -> R.drawable.num_three
                    4 -> R.drawable.num_four
                    5 -> R.drawable.num_five
                    6 -> R.drawable.num_six
                    7 -> R.drawable.num_seven
                    8 -> R.drawable.num_eight
                    9 -> R.drawable.num_nine
                    10 -> R.drawable.num_ten
                    11 -> R.drawable.num_eleven
                    12 -> R.drawable.num_twelve
                    else -> R.drawable.num_one
                }
            )
            if(state.photo != null)
                Picasso.get()
                    .load(state.photo)
                    .resize(500, 500)
                    .into(birthday_picture_iv)
        })

        birthdayViewModel.photoState.observe(viewLifecycleOwner, Observer { path ->
            if(path.toString().isNotBlank())
                startActivityForResult(
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                        putExtra(MediaStore.EXTRA_OUTPUT, path)
                    },
                    REQUEST_CODE_CAMERA
                )
        })
    }

    override fun initUI() {
        selectRandomStyle()

        birthday_close_btn.setOnClickListener {
            findNavController().popBackStack()
        }

        birthday_camera_btn.setOnClickListener {
            checkPermissionAndExecute {
                findNavController().navigate(R.id.action_birthdayFragment_to_imagePickerBottomSheet)
            }
        }

        birthday_share_btn.setOnClickListener {

        }
    }

    override fun actionOnStoragePermissionGranted() {
        findNavController().navigate(R.id.action_birthdayFragment_to_imagePickerBottomSheet)
    }

    override fun openCamera() {
        birthdayViewModel.createPhoto()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_CAMERA -> birthdayViewModel.changePhoto(Uri.EMPTY, isCamera = true)
                REQUEST_CODE_GALLERY -> birthdayViewModel.changePhoto(data?.data?: Uri.EMPTY)
            }
        }
    }

    private fun selectRandomStyle() {
        when(Random.Default.nextInt(3)) {
            0 -> {
                birthday_back_iv.setImageResource(R.drawable.android_elephant_popup)
                birthday_picture_iv.setImageResource(R.drawable.default_place_holder_yellow)
                birthday_camera_btn.setImageResource(R.drawable.ic_camera_yellow)
            }
            1 -> {
                birthday_back_iv.setImageResource(R.drawable.android_fox_popup)
                birthday_picture_iv.setImageResource(R.drawable.default_place_holder_green)
                birthday_camera_btn.setImageResource(R.drawable.ic_camera_green)
            }
            2 -> {
                birthday_back_iv.setImageResource(R.drawable.android_pelican_popup)
                birthday_picture_iv.setImageResource(R.drawable.default_place_holder_blue)
                birthday_camera_btn.setImageResource(R.drawable.ic_camera_blue)
            }
        }
    }

}
