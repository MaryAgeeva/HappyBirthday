package com.mary.happybirthday.di

import com.mary.happybirthday.data.helpers.SharedPreferencesHelper
import com.mary.happybirthday.data.repository.BabyRepository
import com.mary.happybirthday.domain.repository.IBabyRepository
import com.mary.happybirthday.domain.use_cases.detail_screen.ChangeBabyInfoUseCase
import com.mary.happybirthday.domain.use_cases.detail_screen.GetBabyInfoUseCase
import com.mary.happybirthday.presentation.birthday_screen.BirthdayFragment
import com.mary.happybirthday.presentation.birthday_screen.BirthdayViewModel
import com.mary.happybirthday.presentation.detail_screen.DetailFragment
import com.mary.happybirthday.presentation.detail_screen.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single { SharedPreferencesHelper(get()) }
    single<IBabyRepository> { BabyRepository(get()) }
}

val featuresModule = module {
    scope(named<DetailFragment>()) {
        viewModel { DetailViewModel() }
        scoped {
            GetBabyInfoUseCase(get())
        }
        scoped {
            ChangeBabyInfoUseCase(get())
        }
    }

    scope(named<BirthdayFragment>()) {
        viewModel { BirthdayViewModel() }
    }
}