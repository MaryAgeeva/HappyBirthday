package com.mary.happybirthday.presentation.birthday_screen

import androidx.lifecycle.viewModelScope
import com.mary.happybirthday.domain.use_cases.birthday_screen.GetBirthdayInfoUseCase
import com.mary.happybirthday.domain.utils.empty
import com.mary.happybirthday.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class BirthdayViewModel(
    private val getInitAction: GetBirthdayInfoUseCase
) : BaseViewModel<BirthdayViewState>() {

    init {
        getInitialInfo()
    }

    private fun getInitialInfo() {
        viewModelScope.launch {
            try {
                val birthdayInfo = getInitAction()
                val ageUnits = if(birthdayInfo.ageInMonths >= 12) TimeUnit.YEAR else TimeUnit.MONTH
                state.value = BirthdayViewState(
                    name = birthdayInfo.name,
                    age = if(ageUnits == TimeUnit.YEAR)
                        birthdayInfo.ageInMonths / 12
                    else birthdayInfo.ageInMonths,
                    timeUnits = ageUnits,
                    photo = birthdayInfo.photo
                )
            } catch (e: Exception) {
                Timber.e("error occured in getInitAction: $e, message: ${e.message?: String.empty()}")
            }
        }
    }
}
