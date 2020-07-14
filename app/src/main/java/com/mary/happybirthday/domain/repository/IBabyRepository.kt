package com.mary.happybirthday.domain.repository

import com.mary.happybirthday.domain.entity.Baby
import java.util.*

interface IBabyRepository {

    suspend fun getBabyInfo() : Baby

    suspend fun changeName(name: String)

    suspend fun changeBirthday(date: Date)

    suspend fun changePicture(path: String)
}