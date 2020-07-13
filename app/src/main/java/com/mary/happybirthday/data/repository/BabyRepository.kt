package com.mary.happybirthday.data.repository

import com.mary.happybirthday.data.helpers.SharedPreferencesHelper
import com.mary.happybirthday.domain.entity.Baby
import com.mary.happybirthday.domain.repository.IBabyRepository
import com.mary.happybirthday.domain.utils.empty
import java.util.*

class BabyRepository(
    private val sharedPreferences: SharedPreferencesHelper
) : IBabyRepository {

    private lateinit var currentBaby: Baby

    override suspend fun getBabyInfo(): Baby {
        if(!this::currentBaby.isInitialized) {
            val birthday = sharedPreferences.getBirthday()
            currentBaby = Baby(
                name = sharedPreferences.getName()?: String.empty(),
                birthday = if(birthday == 0L) null else Date(birthday),
                photo = sharedPreferences.getPicture()
            )
        }
        return currentBaby
    }

    override suspend fun changeName(name: String) {
        sharedPreferences.setName(name)
        currentBaby = currentBaby.copy(
            name = name
        )
    }

    override suspend fun changeBirthday(day: Int, month: Int, year: Int) {
        val birthday = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_YEAR, day)
            set(Calendar.HOUR, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.time
        sharedPreferences.setBirthday(birthday.time)
        currentBaby = currentBaby.copy(
            birthday = birthday
        )
    }

    override suspend fun changePicture(path: String) {
        sharedPreferences.setPicture(path)
        currentBaby = currentBaby.copy(
            photo = path
        )
    }
}