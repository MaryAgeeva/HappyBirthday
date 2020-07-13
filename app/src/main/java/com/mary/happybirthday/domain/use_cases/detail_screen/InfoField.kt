package com.mary.happybirthday.domain.use_cases.detail_screen

sealed class InfoField

data class Name(val name: String) : InfoField()

data class Birthday(
    val day: Int,
    val month: Int,
    val year: Int
) : InfoField()

data class Picture(val path: String) : InfoField()
