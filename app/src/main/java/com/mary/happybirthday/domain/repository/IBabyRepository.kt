package com.mary.happybirthday.domain.repository

import com.mary.happybirthday.domain.entity.Baby

interface IBabyRepository {

    suspend fun getBabyInfo() : Baby

    suspend fun changeName(name: String)

    suspend fun changeBirthday(day: Int, month: Int, year: Int)

    suspend fun changePicture(path: String)
}