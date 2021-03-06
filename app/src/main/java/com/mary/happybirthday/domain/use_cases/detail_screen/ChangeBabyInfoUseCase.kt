package com.mary.happybirthday.domain.use_cases.detail_screen

import com.mary.happybirthday.domain.entity.Baby
import com.mary.happybirthday.domain.helpers.IShareHelper
import com.mary.happybirthday.domain.repository.IBabyRepository
import com.mary.happybirthday.domain.use_cases.BaseParamsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChangeBabyInfoUseCase(
    private val babyRepository: IBabyRepository,
    private val shareHelper: IShareHelper
) : BaseParamsUseCase<InfoField, Baby> {

    override suspend fun invoke(parameter: InfoField): Baby = withContext(Dispatchers.IO) {
        when(parameter) {
            is Name -> babyRepository.changeName(parameter.name)
            is Birthday -> babyRepository.changeBirthday(parameter.date)
            is Picture -> {
                shareHelper.savePicture(parameter.path.pathToSave)
                babyRepository.changePicture(parameter.path.pathToShow)
            }
        }
        return@withContext babyRepository.getBabyInfo()
    }
}