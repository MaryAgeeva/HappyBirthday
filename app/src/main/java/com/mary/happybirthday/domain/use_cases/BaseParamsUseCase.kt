package com.mary.happybirthday.domain.use_cases

interface BaseParamsUseCase<in P, out R> {

    suspend operator fun invoke(parameter: P) : R
}