package com.mary.happybirthday.domain.use_cases.common

import com.mary.happybirthday.domain.entity.CameraPhoto
import com.mary.happybirthday.domain.helpers.IShareHelper
import com.mary.happybirthday.domain.use_cases.BaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreatePhotoFileUseCase(
    private val shareHelper: IShareHelper
) : BaseUseCase<CameraPhoto> {

    override suspend fun invoke(): CameraPhoto = withContext(Dispatchers.IO) {
        return@withContext shareHelper.createFileForPicture()
    }
}