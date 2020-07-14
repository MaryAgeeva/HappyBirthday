package com.mary.happybirthday.domain.use_cases.birthday_screen

data class BirthdayDTO(
    val name: String,
    val ageInMonths: Int,
    val photo: String?
)