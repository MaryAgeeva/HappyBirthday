package com.mary.happybirthday.presentation.birthday_screen

import android.os.Bundle
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
                Picasso.get().load(state.photo).into(birthday_picture_iv)
        })
    }

    override fun initUI() {
        selectRandomStyle()

        birthday_close_btn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun selectRandomStyle() {
        when(Random.Default.nextInt(3)) {
            0 -> {
                birthday_back_iv.setImageResource(R.drawable.android_elephant_popup)
                birthday_picture_iv.setImageResource(R.drawable.default_place_holder_yellow)
            }
            1 -> {
                birthday_back_iv.setImageResource(R.drawable.android_fox_popup)
                birthday_picture_iv.setImageResource(R.drawable.default_place_holder_green)
            }
            2 -> {
                birthday_back_iv.setImageResource(R.drawable.android_pelican_popup)
                birthday_picture_iv.setImageResource(R.drawable.default_place_holder_blue)
            }
        }
    }

}
