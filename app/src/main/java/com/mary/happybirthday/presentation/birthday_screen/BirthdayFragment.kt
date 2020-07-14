package com.mary.happybirthday.presentation.birthday_screen

import com.mary.happybirthday.R
import com.mary.happybirthday.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.birthday_fragment.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import kotlin.random.Random

class BirthdayFragment : BaseFragment() {

    private val birthdayViewModel: BirthdayViewModel by lifecycleScope.viewModel(this)
    override val viewResource = R.layout.birthday_fragment

    override fun initUI() {
        selectRandomStyle()

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
