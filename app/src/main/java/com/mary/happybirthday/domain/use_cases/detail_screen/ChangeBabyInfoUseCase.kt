package com.mary.happybirthday.domain.use_cases.detail_screen

import com.mary.happybirthday.domain.entity.Baby
import com.mary.happybirthday.domain.repository.IBabyRepository
import com.mary.happybirthday.domain.use_cases.BaseParamsUseCase

class ChangeBabyInfoUseCase(
    private val babyRepository: IBabyRepository
) : BaseParamsUseCase<InfoField, Baby> {

    override suspend fun invoke(parameter: InfoField): Baby {
        when(parameter) {
            is Name -> babyRepository.changeName(parameter.name)
            is Birthday -> babyRepository.changeBirthday(parameter.day, parameter.month, parameter.year)
            is Picture -> babyRepository.changePicture(parameter.path)
        }
        return babyRepository.getBabyInfo()
    }
}