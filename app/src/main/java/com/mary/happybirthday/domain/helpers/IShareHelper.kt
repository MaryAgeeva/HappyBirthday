package com.mary.happybirthday.domain.helpers

import android.graphics.Bitmap

interface IShareHelper {

    suspend fun saveCard(bitmap: Bitmap) : String
}