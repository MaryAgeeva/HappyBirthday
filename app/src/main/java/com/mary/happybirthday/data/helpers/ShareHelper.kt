package com.mary.happybirthday.data.helpers

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import com.mary.happybirthday.domain.helpers.IShareHelper
import com.mary.happybirthday.domain.utils.FileNotSavedException
import com.mary.happybirthday.domain.utils.empty
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ShareHelper(
    private val context: Context
) : IShareHelper {

    @Throws(
        FileNotSavedException::class
    )
    override suspend fun saveCard(bitmap: Bitmap): String {
        val (folderPath, filePath) = createFilePath()
        return try {
            val folder = File(folderPath)
            if(!folder.exists())
                folder.mkdir()
            val file = File(filePath)
            FileOutputStream(file).use { stream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.flush()
            }
            filePath
        } catch (e: Exception) {
            Timber.e("exception occured while saving screenshot ShareHelper: $e, message: ${
                e.message?: String.empty()
            }")
            throw FileNotSavedException()
        }
    }

    private fun createFilePath(): Paths {
        val folder = "${context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)}$FOLDER"
        return Paths(
            folder = folder,
            file = "$folder/$FILE_NAME${Date().toReportDate()}.$FORMAT"
        )
    }

    private fun Date.toReportDate() : String {
        return SimpleDateFormat(REPORT_DATE_FORMAT, Locale.getDefault()).format(this)
    }

    private data class Paths(
        val folder: String,
        val file: String
    )

    private companion object {
        const val FOLDER = "/Birthday"
        const val FILE_NAME = "Card_"
        const val FORMAT = ".png"

        const val REPORT_DATE_FORMAT = "dd/MM/yyyy-HH/mm/ss"
    }
}