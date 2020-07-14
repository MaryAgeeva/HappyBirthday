package com.mary.happybirthday.domain.use_cases.birthday_screen

import com.mary.happybirthday.domain.entity.Baby
import com.mary.happybirthday.domain.repository.IBabyRepository
import com.mary.happybirthday.domain.use_cases.BaseParamsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChangePhotoUseCase(
    private val babyRepository: IBabyRepository
) : BaseParamsUseCase<String, Baby> {

    override suspend fun invoke(parameter: String): Baby = withContext(Dispatchers.IO) {
        babyRepository.changePicture(parameter)
        return@withContext babyRepository.getBabyInfo()
    }
}