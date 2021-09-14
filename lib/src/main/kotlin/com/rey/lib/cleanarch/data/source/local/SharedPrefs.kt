package com.rey.lib.cleanarch.data.source.local

import android.content.SharedPreferences
import com.rey.lib.cleanarch.data.repository.source.local.ISharedPrefs

class SharedPrefs(private val pref: SharedPreferences) : ISharedPrefs {
    override suspend fun set(key: String, value: Any): Unit = with(pref.edit()) {
        when (value) {
            is Boolean -> putBoolean(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Float -> putFloat(key, value)
            else -> throw IllegalArgumentException("unsupported data type ${value.javaClass}")
        }
        apply()
    }

    override suspend fun getPref(): SharedPreferences = pref
}