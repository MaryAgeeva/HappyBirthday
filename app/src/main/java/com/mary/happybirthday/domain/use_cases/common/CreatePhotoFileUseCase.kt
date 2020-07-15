package com.mary.happybirthday.domain.use_cases.common

import android.net.Uri
import com.mary.happybirthday.domain.helpers.IShareHelper
import com.mary.happybirthday.domain.use_cases.BaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreatePhotoFileUseCase(
    private val shareHelper: IShareHelper
) : BaseUseCase<Uri> {

    override suspend fun invoke(): Uri = withContext(Dispatchers.IO) {
        return@withContext shareHelper.createFileForPicture()
    }
}