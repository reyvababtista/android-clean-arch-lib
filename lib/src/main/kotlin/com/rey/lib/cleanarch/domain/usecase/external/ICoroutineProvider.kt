package com.rey.lib.cleanarch.domain.usecase.external

import kotlinx.coroutines.CoroutineDispatcher

interface ICoroutineProvider {
    fun main(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
}