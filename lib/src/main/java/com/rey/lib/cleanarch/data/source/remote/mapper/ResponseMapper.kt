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
package com.rey.lib.cleanarch.data.source.remote.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rey.lib.cleanarch.domain.dto.Result
import com.rey.lib.cleanarch.domain.dto.UNKNOWN_ERROR
import com.rey.lib.cleanarch.domain.mapper.Mapper
import okhttp3.ResponseBody
import retrofit2.Response

abstract class ResponseMapper<RequestModel, ResponseModel>(private val gson: Gson) :
    Mapper<RequestModel, ResponseModel> {

    protected lateinit var retrofitResponse: Response<RequestModel>

    protected open fun mapErrorMessage(errorBody: ResponseBody): String =
        gson.fromJson(errorBody.charStream().readText(), object : TypeToken<String>() {}.type)

    private fun mapException(errorBody: ResponseBody): Exception =
        Exception(mapErrorMessage(errorBody))

    private fun mapError(response: Response<RequestModel>): Result<ResponseModel> = with(response) {
        val exception = try {
            mapException(errorBody = errorBody() ?: throw Exception(UNKNOWN_ERROR))
        } catch (e: Exception) {
            e
        }

        Result.Error(exception = exception, code = code())
    }

    @Suppress("UNCHECKED_CAST")
    open fun mapResponse(response: Response<RequestModel>): Result<ResponseModel> = with(response) {
        retrofitResponse = this
        val responseBody = body()

        if (isSuccessful) {
            if (responseBody != null) Result.Success(map(responseBody))
            else Result.Success(Unit as ResponseModel)
        } else {
            mapError(response = response)
        }
    }
}