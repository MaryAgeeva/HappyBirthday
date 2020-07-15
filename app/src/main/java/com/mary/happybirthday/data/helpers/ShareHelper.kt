package com.mary.happybirthday.data.helpers

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.mary.happybirthday.domain.entity.CameraPhoto
import com.mary.happybirthday.domain.helpers.IShareHelper
import com.mary.happybirthday.domain.utils.FileNotCreatedException
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

    @Throws(
        FileNotCreatedException::class
    )
    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun createFileForPicture(): CameraPhoto {
        try {
            val timeStamp: String = Date().toReportDate()
            val storageDir: File = context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES
            )?.absoluteFile?: throw FileNotCreatedException()
            val file = File.createTempFile(
                "$FILE_NAME_PHOTO${timeStamp}_",
                FORMAT,
                storageDir
            )
            return CameraPhoto(
                temporaryUri = getUri(file),
                absolutePath = file.absolutePath
            )
        } catch (e: Exception) {
            Timber.e("exception occured while creating Uri ShareHelper: $e, message: ${
            e.message?: String.empty()
            }")
            throw FileNotCreatedException()
        }
    }

    @Throws(
        FileNotSavedException::class
    )
    override suspend fun savePicture(path: String) {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(path)
            mediaScanIntent.data = Uri.fromFile(f)
            context.sendBroadcast(mediaScanIntent)
        }
    }

    private fun createFilePath(forPhoto: Boolean = false): Paths {
        val folder = "${context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)}$FOLDER"
        return Paths(
            folder = folder,
            file = "$folder/${if(forPhoto) FILE_NAME_PHOTO else FILE_NAME}${Date().toReportDate()}.$FORMAT"
        )
    }

    private fun getUri(file: File) : Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.applicationContext.packageName}.provider",
            file
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
        const val FOLDER = "/HappyBirthday"
        const val FILE_NAME = "Card_"
        const val FILE_NAME_PHOTO = "Photo_"
        const val FORMAT = ".jpg"

        const val REPORT_DATE_FORMAT = "dd_MM_yyyy_HH_mm_ss"
    }
}