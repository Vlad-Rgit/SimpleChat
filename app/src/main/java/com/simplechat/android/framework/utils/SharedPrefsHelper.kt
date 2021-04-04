package com.simplechat.android.framework.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsHelper @Inject constructor(
    @ApplicationContext context: Context
) {
    companion object {
        val privatePrefsName = "com.simplechat.android.private"

        val userLoginKey = "userLoginKey"
        val userPasswordKey = "userPasswordKey"
    }

    private val prefs = context
        .getSharedPreferences(privatePrefsName, Context.MODE_PRIVATE)


    fun contains(key: String): Boolean {
        return prefs.contains(key)
    }

    fun readString(key: String, defValue: String? = ""): String? {
        return prefs.getString(
            key,
            defValue
        )
    }

    fun writeString(key: String, value: String?) {
        prefs.edit()
            .putString(key, value)
            .apply()
    }


    fun remove(key: String) {
        prefs.edit()
            .remove(key)
            .apply()
    }

}