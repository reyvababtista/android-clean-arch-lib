/**
 * Copyright (C) 2021 Reyva Babtista
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rey.lib.cleanarch.domain.dto

sealed class Result<out T> {
    data class Success<out S>(
        val data: S
    ) : Result<S>()

    data class Error(
        val exception: Exception = Exception(UNKNOWN_ERROR),
        val code: Int = -1
    ) : Result<Nothing>()
}

suspend fun <T> suspendTryCatch(
    codeBlock: suspend () -> Result<T>
): Result<T> = try {
    codeBlock()
} catch (e: Exception) {
    Result.Error(e)
}

fun <T> tryCatch(
    codeBlock: () -> Result<T>
): Result<T> = try {
    codeBlock()
} catch (e: Exception) {
    Result.Error(e)
}

const val UNKNOWN_ERROR = "unknown error"

suspend fun <T, S> Result<T>.next(nextFunc: suspend (result: Result<T>) -> Result<S>): Result<S> =
    when (this) {
        is Result.Success -> nextFunc(this)
        is Result.Error -> this
    }

suspend fun <T, S> Result<T>.nextOnError(func: suspend (result: Result<T>) -> Result<S>): Result<S> =
    if (this is Result.Error) func(this) else this as Result<S>

suspend fun <T> Result<T>.onSuccess(func: suspend (data: T) -> Unit): Result<T> =
    this.apply { if (this is Result.Success) func(this.data) }

suspend fun <T> Result<T>.onError(func: suspend (err: Result.Error) -> Unit): Result<T> =
    this.apply { if (this is Result.Error) func(this) }

val <T> Result<T>.data: T
    get() = when (this) {
        is Result.Success -> this.data
        is Result.Error -> throw exception
    }

val <T> Result<T>.errorMessage: String
    get() = (this as Result.Error).exception.localizedMessage
        ?: throw IllegalStateException(UNKNOWN_ERROR)

val <T> Result<T>.errorCode: Int
    get() = (this as Result.Error).code

val <T> Result<T>.error: Exception
    get() = (this as Result.Error).exception
