package com.mary.happybirthday.presentation.detail_screen

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.mary.happybirthday.R
import com.mary.happybirthday.presentation.base.BaseFragment
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
            detail_name_edit.setText(state.name)
            detail_dob_edit.setText(state.birthday)
            state.picture?.let {
                Picasso.get().load(state.picture).into(detail_profile_iv)
            }?: detail_profile_iv.setImageResource(R.drawable.default_place_holder_green)
            detail_show_btn.isEnabled = state.canShowInfo
        })
    }

    override fun initUI() {
        detail_dob_txt.setOnClickListener {
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

    companion object {
        private const val PICKER_TAG = "com.mary.happybirthday.presentation.detail_screen.DetailFragment.MaterialDatePicker"
    }
}
