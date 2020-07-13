package com.mary.happybirthday.domain.entity

import com.mary.happybirthday.domain.utils.empty
import java.util.*

data class Baby(
    val name: String = String.empty(),
    val birthday: Date? = null,
    val photo: String? = null
)