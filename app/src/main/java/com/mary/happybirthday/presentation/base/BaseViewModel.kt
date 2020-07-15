package com.mary.happybirthday.presentation.base

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mary.happybirthday.domain.use_cases.common.CreatePhotoFileUseCase
import com.mary.happybirthday.domain.utils.empty
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<S>(
    private val createPhotoAction: CreatePhotoFileUseCase
) : ViewModel() {

    protected val state: MutableLiveData<S> = MutableLiveData()
    internal val viewState: LiveData<S> = state

    protected val photo: MutableLiveData<Uri> = MutableLiveData()
    internal val photoState: LiveData<Uri> = this.photo

    internal fun createPhoto() {
        viewModelScope.launch {
            try {
                val fileUri = createPhotoAction()
                this@BaseViewModel.photo.value = fileUri
            } catch (e: Exception) {
                Timber.e("error occured in createPhoto: $e, message: ${e.message?: String.empty()}")
            }
        }
    }
}