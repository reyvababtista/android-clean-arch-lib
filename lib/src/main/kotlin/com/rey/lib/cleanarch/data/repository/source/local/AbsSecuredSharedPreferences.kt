package com.rey.lib.cleanarch.data.repository.source.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

abstract class AbsSecuredSharedPreferences(context: Context) {
    protected open val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    protected open val mainKeyAlias by lazy { MasterKeys.getOrCreate(keyGenParameterSpec) }
    protected open val prefKey = "default"
    protected open val pref: SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            prefKey,
            mainKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    protected fun set(key: String, value: Any) = with(pref.edit()) {
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
}