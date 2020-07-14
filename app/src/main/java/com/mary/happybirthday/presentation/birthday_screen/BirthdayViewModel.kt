package com.mary.happybirthday.presentation.birthday_screen

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.mary.happybirthday.domain.use_cases.birthday_screen.ChangePhotoUseCase
import com.mary.happybirthday.domain.use_cases.birthday_screen.GetBirthdayInfoUseCase
import com.mary.happybirthday.domain.utils.empty
import com.mary.happybirthday.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

internal class BirthdayViewModel(
    private val getInitAction: GetBirthdayInfoUseCase,
    private val changePhotoAction: ChangePhotoUseCase
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

    internal fun changePhoto(path: Uri) {
        viewModelScope.launch {
            val photo = path.toString()
            state.value = state.value?.copy(
                photo = photo
            )
            try {
                changePhotoAction(photo)
            } catch (e: Exception) {
                Timber.e("error occured in changePhoto: $e, message: ${e.message?: String.empty()}")
            }
        }
    }

    internal fun shareScreenshot() {
        viewModelScope.launch {

        }
    }
}
