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