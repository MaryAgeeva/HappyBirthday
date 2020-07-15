package com.mary.happybirthday.presentation.birthday_screen

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mary.happybirthday.domain.entity.CameraPhoto
import com.mary.happybirthday.domain.use_cases.birthday_screen.ChangePhotoUseCase
import com.mary.happybirthday.domain.use_cases.birthday_screen.GetBirthdayInfoUseCase
import com.mary.happybirthday.domain.use_cases.birthday_screen.SaveInfoCardUseCase
import com.mary.happybirthday.domain.use_cases.common.CreatePhotoFileUseCase
import com.mary.happybirthday.domain.use_cases.common.Photo
import com.mary.happybirthday.domain.utils.empty
import com.mary.happybirthday.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

internal class BirthdayViewModel(
    private val getInitAction: GetBirthdayInfoUseCase,
    private val changePhotoAction: ChangePhotoUseCase,
    private val saveInfoCardAction: SaveInfoCardUseCase,
    createPhotoAction: CreatePhotoFileUseCase
) : BaseViewModel<BirthdayViewState>(createPhotoAction) {

    private val report: MutableLiveData<Uri> = MutableLiveData()
    internal val reportState: LiveData<Uri> = this.report

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

    internal fun changePhoto(path: Uri, isCamera: Boolean = false) {
        viewModelScope.launch {
            val resultPhoto = Photo(
                pathToSave = if(isCamera) (photo.value?.absolutePath?: String.empty()) else path.toString(),
                pathToShow = if(isCamera) (photo.value?.temporaryUri?.toString()?: String.empty()) else path.toString()
            )
            try {
                changePhotoAction(resultPhoto)
                photo.value = CameraPhoto()
                state.value = state.value?.copy(
                    photo = resultPhoto.pathToShow
                )
            } catch (e: Exception) {
                Timber.e("error occured in changePhoto: $e, message: ${e.message?: String.empty()}")
            }
        }
    }

    internal fun shareScreenshot(bitmap: Bitmap) {
        viewModelScope.launch {
            try {
                val resultPath = saveInfoCardAction(bitmap)
                report.value = resultPath
            } catch (e: Exception) {
                Timber.e("error occured in shareScreenshot: $e, message: ${e.message?: String.empty()}")
            }
        }
    }
}
