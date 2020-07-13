package com.mary.happybirthday.data.helpers

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(TITLE, MODE_PRIVATE)
    }

    internal fun getName() : String? = getString(NAME)

    internal fun getBirthday() : Long = getLong(BIRTHDAY)

    internal fun getPicture() : String? = getString(PICTURE)

    internal fun setName(name: String) {
        putString(NAME, name)
    }

    internal fun setBirthday(birthday: Long) {
        putLong(BIRTHDAY, birthday)
    }

    internal fun setPicture(picture: String) {
        putString(PICTURE, picture)
    }

    private fun getString(key: String) : String? {
        return sharedPreferences.getString(key, null)
    }

    private fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    private fun getLong(key: String) : Long {
        return sharedPreferences.getLong(key, 0L)
    }

    private fun putLong(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    private companion object {
        const val TITLE = "com.mary.happybirthday.data.helpers.SharedPreferencesHelper"

        const val NAME = "com.mary.happybirthday.data.helpers.SharedPreferencesHelper.NAME"
        const val BIRTHDAY = "com.mary.happybirthday.data.helpers.SharedPreferencesHelper.BIRTHDAY"
        const val PICTURE = "com.mary.happybirthday.data.helpers.SharedPreferencesHelper.PICTURE"
    }
}