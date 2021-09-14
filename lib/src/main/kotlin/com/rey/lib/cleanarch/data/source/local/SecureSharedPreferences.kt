package com.rey.lib.cleanarch.data.source.local

import android.content.SharedPreferences
import com.rey.lib.cleanarch.data.repository.source.local.ISecureSharedPreferences
import com.rey.lib.cleanarch.domain.dto.Result
import com.rey.lib.cleanarch.domain.dto.suspendTryCatch

class SecureSharedPreferences(private val pref: SharedPreferences) : ISecureSharedPreferences {
    override suspend fun set(key: String, value: Any): Result<Unit> = suspendTryCatch {
        with(pref.edit()) {
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

        Result.Success(Unit)
    }
}