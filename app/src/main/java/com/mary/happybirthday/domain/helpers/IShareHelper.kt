package com.mary.happybirthday.domain.helpers

import android.graphics.Bitmap
import android.net.Uri
import com.mary.happybirthday.domain.entity.CameraPhoto
import com.mary.happybirthday.domain.utils.FileNotCreatedException
import com.mary.happybirthday.domain.utils.FileNotSavedException

interface IShareHelper {

    @Throws(
        FileNotSavedException::class
    )
    suspend fun saveCard(bitmap: Bitmap) : Uri

    @Throws(
        FileNotCreatedException::class
    )
    suspend fun createFileForPicture() : CameraPhoto

    @Throws(
        FileNotSavedException::class
    )
    suspend fun savePicture(path: String)
}