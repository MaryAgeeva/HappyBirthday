package com.mary.happybirthday.presentation.detail_screen

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.mary.happybirthday.domain.entity.CameraPhoto
import com.mary.happybirthday.domain.use_cases.common.CreatePhotoFileUseCase
import com.mary.happybirthday.domain.use_cases.common.Photo
import com.mary.happybirthday.domain.use_cases.detail_screen.*
import com.mary.happybirthday.domain.utils.empty
import com.mary.happybirthday.presentation.base.BaseViewModel
import com.mary.happybirthday.presentation.utils.toDateString
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

internal class DetailViewModel(
    private val getInitAction: GetBabyInfoUseCase,
    private val changeInfoAction: ChangeBabyInfoUseCase,
    createPhotoAction: CreatePhotoFileUseCase
) : BaseViewModel<DetailViewState>(createPhotoAction) {

    internal fun getInitialInfo() {
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

    internal fun changeName(name: String) {
        viewModelScope.launch {
            try {
                val birthday = state.value?.birthday?: String.empty()
                changeInfoAction(Name(name))
                state.value = state.value?.copy(
                    name = name,
                    canShowInfo = birthday.isNotBlank() && name.isNotBlank()
                )
            } catch (e: Exception) {
                Timber.e("error occured in changeName: $e, message: ${e.message?: String.empty()}")
            }
        }
    }

    internal fun changeBirthday(millis: Long) {
        viewModelScope.launch {
            val birthday = Date(millis)
            val name = state.value?.name?: String.empty()
            state.value = state.value?.copy(
                birthday = birthday.toDateString(),
                canShowInfo = name.isNotBlank()
            )
            try {
                changeInfoAction(Birthday(birthday))
            } catch (e: Exception) {
                Timber.e("error occured in changeBirthday: $e, message: ${e.message?: String.empty()}")
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
                changeInfoAction(Picture(resultPhoto))
                photo.value = CameraPhoto()
                state.value = state.value?.copy(
                    picture = resultPhoto.pathToShow
                )
            } catch (e: Exception) {
                Timber.e("error occured in changePhoto: $e, message: ${e.message?: String.empty()}")
            }
        }
    }
}
