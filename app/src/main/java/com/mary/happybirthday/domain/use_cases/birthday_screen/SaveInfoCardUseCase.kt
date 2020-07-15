package com.mary.happybirthday.domain.use_cases.birthday_screen

import android.graphics.Bitmap
import android.net.Uri
import com.mary.happybirthday.domain.helpers.IShareHelper
import com.mary.happybirthday.domain.use_cases.BaseParamsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveInfoCardUseCase(
    private val shareHelper: IShareHelper
) : BaseParamsUseCase<Bitmap, Uri> {

    override suspend fun invoke(parameter: Bitmap): Uri  = withContext(Dispatchers.IO) {
        return@withContext shareHelper.saveCard(parameter)
    }
}