package com.mary.happybirthday.presentation.detail_screen

data class DetailViewState(
    val picture: String?,
    val name: String,
    val birthday: String,
    val canShowInfo: Boolean
)