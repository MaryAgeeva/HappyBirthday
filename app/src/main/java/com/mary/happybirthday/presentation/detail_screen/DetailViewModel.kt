package com.mary.happybirthday.presentation.detail_screen

import androidx.lifecycle.viewModelScope
import com.mary.happybirthday.domain.use_cases.detail_screen.Birthday
import com.mary.happybirthday.domain.use_cases.detail_screen.ChangeBabyInfoUseCase
import com.mary.happybirthday.domain.use_cases.detail_screen.GetBabyInfoUseCase
import com.mary.happybirthday.domain.utils.empty
import com.mary.happybirthday.presentation.base.BaseViewModel
import com.mary.happybirthday.presentation.utils.toDateString
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

internal class DetailViewModel(
    private val getInitAction: GetBabyInfoUseCase,
    private val changeInfoAction: ChangeBabyInfoUseCase
) : BaseViewModel<DetailViewState>() {

    init {
        getInitialInfo()
    }

    internal fun changeBirthday(millis: Long) {
        viewModelScope.launch {
            val birthday = Date(millis)
            state.value = state.value?.copy(
                birthday = birthday.toDateString()
            )
            try {
                changeInfoAction(Birthday(birthday))
            } catch (e: Exception) {
                Timber.e("error occured in changeBirthday: $e, message: ${e.message?: String.empty()}")
            }
        }
    }

    private fun getInitialInfo() {
        viewModelScope.launch {
            try {
                val baby = getInitAction()
                val birthday = baby.birthday?.toDateString() ?: String.empty()
                state.value = DetailViewState(
                    picture = baby.photo,
                    name = baby.name,
                    birthday = birthday,
                    canShowInfo = baby.name.isNotBlank() && birthday.isNotBlank()
                )
            } catch (e: Exception) {
                Timber.e("error occured in getInitAction: $e, message: ${e.message?: String.empty()}")
            }
        }
    }
}
