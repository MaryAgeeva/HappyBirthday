package com.mary.happybirthday.di

import com.mary.happybirthday.data.helpers.ShareHelper
import com.mary.happybirthday.data.helpers.SharedPreferencesHelper
import com.mary.happybirthday.data.repository.BabyRepository
import com.mary.happybirthday.domain.helpers.IShareHelper
import com.mary.happybirthday.domain.repository.IBabyRepository
import com.mary.happybirthday.domain.use_cases.birthday_screen.ChangePhotoUseCase
import com.mary.happybirthday.domain.use_cases.birthday_screen.GetBirthdayInfoUseCase
import com.mary.happybirthday.domain.use_cases.birthday_screen.SaveInfoCardUseCase
import com.mary.happybirthday.domain.use_cases.common.CreatePhotoFileUseCase
import com.mary.happybirthday.domain.use_cases.detail_screen.ChangeBabyInfoUseCase
import com.mary.happybirthday.domain.use_cases.detail_screen.GetBabyInfoUseCase
import com.mary.happybirthday.presentation.birthday_screen.BirthdayFragment
import com.mary.happybirthday.presentation.birthday_screen.BirthdayViewModel
import com.mary.happybirthday.presentation.detail_screen.DetailFragment
import com.mary.happybirthday.presentation.detail_screen.DetailViewModel
import com.mary.happybirthday.presentation.utils.ScreenshotHelper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single { SharedPreferencesHelper(get()) }
    single<IBabyRepository> { BabyRepository(get()) }
    single<IShareHelper> { ShareHelper(get()) }
}

val featuresModule = module {
    factory {
        CreatePhotoFileUseCase(get())
    }

    scope(named<DetailFragment>()) {
        viewModel { DetailViewModel(get(), get(), get()) }
        scoped {
            GetBabyInfoUseCase(get())
        }
        scoped {
            ChangeBabyInfoUseCase(get(), get())
        }
    }

    scope(named<BirthdayFragment>()) {
        viewModel { BirthdayViewModel(get(), get(), get(), get()) }
        scoped {
            ScreenshotHelper()
        }
        scoped {
            GetBirthdayInfoUseCase(get())
        }
        scoped {
            ChangePhotoUseCase(get(), get())
        }
        scoped {
            SaveInfoCardUseCase(get())
        }
    }
}