package com.rey.lib.cleanarch.data.repository.source.local

import android.content.SharedPreferences
import com.rey.lib.cleanarch.domain.dto.Result

interface ISharedPrefs {
    suspend fun set(key: String, value: Any): Result<Unit>
    suspend fun getPref(): Result<SharedPreferences>
}