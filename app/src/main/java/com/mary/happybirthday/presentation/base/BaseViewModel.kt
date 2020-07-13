package com.mary.happybirthday.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<S> : ViewModel() {

    protected val state: MutableLiveData<S> = MutableLiveData()
    internal val viewState: LiveData<S> = state
}