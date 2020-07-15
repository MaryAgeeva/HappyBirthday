package com.mary.happybirthday.domain.entity

import android.net.Uri
import com.mary.happybirthday.domain.utils.empty

data class CameraPhoto(
    val temporaryUri: Uri = Uri.EMPTY,
    val absolutePath: String = String.empty()
)