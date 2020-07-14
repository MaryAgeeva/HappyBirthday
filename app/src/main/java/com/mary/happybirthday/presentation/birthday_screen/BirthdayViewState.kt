package com.mary.happybirthday.presentation.birthday_screen

data class BirthdayViewState(
    val name: String,
    val age: Int,
    val timeUnits: TimeUnit,
    val photo: String?
)

enum class TimeUnit {
    MONTH, YEAR
}