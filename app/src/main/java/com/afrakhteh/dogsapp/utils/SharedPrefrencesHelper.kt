package com.afrakhteh.dogsapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferencesHelper {


    companion object {
        private var SHARED: SharedPreferences? = null

        @Volatile
        private var INSTANCE: SharedPreferencesHelper? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): SharedPreferencesHelper =
                INSTANCE ?: synchronized(LOCK) {
                    INSTANCE ?: buildShared(context).also {
                        INSTANCE = it
                    }
                }

        private fun buildShared(context: Context): SharedPreferencesHelper {
            SHARED = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }
    }

    fun timeSaver(time: Long) {
        SHARED?.edit(commit = true) {
            putLong(Constant.TIME_SHARED_SAVE, time)
        }
    }

    fun getUpdateTime() = SHARED?.getLong(Constant.TIME_SHARED_SAVE, 0)

    fun getCacheDuration() = SHARED?.getString("pref_duration", "")
}