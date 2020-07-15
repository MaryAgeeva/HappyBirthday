package com.mary.happybirthday.presentation.utils

import android.graphics.Bitmap
import android.view.View
import com.mary.happybirthday.domain.utils.empty
import timber.log.Timber

class ScreenshotHelper {

    internal fun takeScreenshot(view: View) : Bitmap? {
        return try {
            with(view) {
                isDrawingCacheEnabled = true
                val bitmap = Bitmap.createBitmap(view.drawingCache)
                isDrawingCacheEnabled = false
                return@with bitmap
            }
        } catch (e: Exception) {
            Timber.e("exception in takeScreenshot: $e, message: ${e.message?: String.empty()}")
            null
        }
    }
}