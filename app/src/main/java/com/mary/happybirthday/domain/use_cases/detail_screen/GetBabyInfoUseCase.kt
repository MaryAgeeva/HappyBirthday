package com.mary.happybirthday.domain.use_cases.detail_screen

import com.mary.happybirthday.domain.entity.Baby
import com.mary.happybirthday.domain.repository.IBabyRepository
import com.mary.happybirthday.domain.use_cases.BaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetBabyInfoUseCase(
    private val babyRepository: IBabyRepository
) : BaseUseCase<Baby> {

    override suspend fun invoke(): Baby = withContext(Dispatchers.IO) {
        babyRepository.getBabyInfo()
    }
}