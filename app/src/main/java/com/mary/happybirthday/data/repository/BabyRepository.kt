package com.mary.happybirthday.data.repository

import com.mary.happybirthday.data.helpers.SharedPreferencesHelper
import com.mary.happybirthday.domain.entity.Baby
import com.mary.happybirthday.domain.repository.IBabyRepository
import com.mary.happybirthday.domain.utils.empty
import java.util.*

class BabyRepository(
    private val sharedPreferences: SharedPreferencesHelper
) : IBabyRepository {

    private var currentBaby: Baby? = null

    override suspend fun getBabyInfo(): Baby {
        if(currentBaby == null) {
            val birthday = sharedPreferences.getBirthday()
            currentBaby = Baby(
                name = sharedPreferences.getName()?: String.empty(),
                birthday = if(birthday == 0L) null else Date(birthday),
                photo = sharedPreferences.getPicture()
            )
        }
        return currentBaby as Baby
    }

    override suspend fun changeName(name: String) {
        sharedPreferences.setName(name)
        currentBaby = currentBaby?.copy(
            name = name
        )
    }

    override suspend fun changeBirthday(date: Date) {
        sharedPreferences.setBirthday(date.time)
        currentBaby = currentBaby?.copy(
            birthday = date
        )
    }

    override suspend fun changePicture(path: String) {
        sharedPreferences.setPicture(path)
        currentBaby = currentBaby?.copy(
            photo = path
        )
    }
}