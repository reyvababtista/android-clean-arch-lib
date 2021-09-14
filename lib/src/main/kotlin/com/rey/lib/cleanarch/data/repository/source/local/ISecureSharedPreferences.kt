package com.rey.lib.cleanarch.data.repository.source.local

import com.rey.lib.cleanarch.domain.dto.Result

interface ISecureSharedPreferences {
    suspend fun set(key: String, value: Any): Result<Unit>
}