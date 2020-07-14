package com.mary.happybirthday.domain.use_cases.birthday_screen

import com.mary.happybirthday.domain.repository.IBabyRepository
import com.mary.happybirthday.domain.use_cases.BaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class GetBirthdayInfoUseCase(
    private val babyRepository: IBabyRepository
) : BaseUseCase<BirthdayDTO> {

    override suspend fun invoke(): BirthdayDTO = withContext(Dispatchers.IO) {
        val baby = babyRepository.getBabyInfo()

        val now = Calendar.getInstance()
        val birthday = Calendar.getInstance().apply {
            if(baby.birthday != null)
                time = baby.birthday
        }

        val modifier = if(now.get(Calendar.DAY_OF_MONTH) >= birthday.get(Calendar.DAY_OF_MONTH))
            0
        else 1

        val ageInMonths = ((now.get(Calendar.YEAR) - 1) * 12 + now.get(Calendar.MONTH)) -
                ((birthday.get(Calendar.YEAR) - 1) * 12 + birthday.get(Calendar.MONTH))-
                modifier

        return@withContext BirthdayDTO(
            name = baby.name,
            ageInMonths = ageInMonths,
            photo = baby.photo
        )
    }
}