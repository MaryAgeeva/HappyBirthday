package com.mary.happybirthday.domain.use_cases.detail_screen

import java.util.*

sealed class InfoField

data class Name(val name: String) : InfoField()

data class Birthday(val date: Date) : InfoField()

data class Picture(val path: String) : InfoField()
