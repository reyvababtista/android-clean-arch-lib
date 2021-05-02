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
