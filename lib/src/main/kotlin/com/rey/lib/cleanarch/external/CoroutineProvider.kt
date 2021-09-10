package com.rey.lib.cleanarch.external

import com.rey.lib.cleanarch.domain.usecase.external.ICoroutineProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CoroutineProvider : ICoroutineProvider {
    override fun main(): CoroutineDispatcher = Dispatchers.Main

    override fun io(): CoroutineDispatcher = Dispatchers.IO

    override fun default(): CoroutineDispatcher = Dispatchers.Default
}