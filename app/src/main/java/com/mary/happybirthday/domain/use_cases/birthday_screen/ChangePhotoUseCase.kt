package com.mary.happybirthday.domain.use_cases.birthday_screen

import com.mary.happybirthday.domain.entity.Baby
import com.mary.happybirthday.domain.helpers.IShareHelper
import com.mary.happybirthday.domain.repository.IBabyRepository
import com.mary.happybirthday.domain.use_cases.BaseParamsUseCase
import com.mary.happybirthday.domain.use_cases.common.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChangePhotoUseCase(
    private val babyRepository: IBabyRepository,
    private val shareHelper: IShareHelper
) : BaseParamsUseCase<Photo, Baby> {

    override suspend fun invoke(parameter: Photo): Baby = withContext(Dispatchers.IO) {
        shareHelper.savePicture(parameter.pathToSave)
        babyRepository.changePicture(parameter.pathToShow)
        return@withContext babyRepository.getBabyInfo()
    }
}