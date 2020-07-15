package com.mary.happybirthday.domain.use_cases.detail_screen

import com.mary.happybirthday.domain.use_cases.common.Photo
import java.util.*

sealed class InfoField

data class Name(val name: String) : InfoField()

data class Birthday(val date: Date) : InfoField()

data class Picture(val path: Photo) : InfoField()
