package com.rey.lib.cleanarch.data.repository.source.local

import android.content.SharedPreferences

interface ISharedPrefs {
    suspend fun set(key: String, value: Any): Unit
    suspend fun getPref(): SharedPreferences
}