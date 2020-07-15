package com.mary.happybirthday.presentation.birthday_screen

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import androidx.annotation.DrawableRes
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

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //orientation locked for reasons to create uniformed user birthday card
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        birthdayViewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            birthday_header_tv.text = getString(R.string.birthday_title, state.name)
            birthday_footer_tv.text = resources.getQuantityString(
                if(state.timeUnits == TimeUnit.MONTH) R.plurals.birthday_months
                else R.plurals.birthday_years,
                state.age
            )
            birthday_age_iv.setImageResource(mapAge(state.age))
            if(state.photo != null)
                Picasso.get()
                    .load(state.photo)
                    .resize(500, 500)
                    .into(birthday_picture_iv)
        })

        birthdayViewModel.photoState.observe(viewLifecycleOwner, Observer { path ->
            if(path.temporaryUri.toString().isNotBlank())
                openCamera(path.temporaryUri)
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

    override fun savePhoto(path: Uri, isCameraPhoto: Boolean) {
        birthdayViewModel.changePhoto(path, isCameraPhoto)
    }

    @DrawableRes private fun mapAge(age: Int) : Int {
        return when(age) {
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
