package com.mary.happybirthday.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet : BottomSheetDialogFragment() {

    protected abstract val viewResource: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(viewResource, container, false)
    }
}