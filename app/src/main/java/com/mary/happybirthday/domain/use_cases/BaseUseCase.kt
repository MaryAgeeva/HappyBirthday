package com.mary.happybirthday.domain.use_cases

interface BaseUseCase<out R> {

    suspend operator fun invoke() : R
}