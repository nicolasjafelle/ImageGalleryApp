package com.nicolas.imagegallery.api

import com.google.gson.Gson
import com.nicolas.imagegallery.api.response.ErrorResponse
import com.nicolas.imagegallery.utils.ErrorResponseException
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

object ResponseParser {


    private fun parse(response: String?, clazz: Class<out Any>): Any {

        val result: Any

        if (response == null) {
            throw Exception("RESPONSE IS NULL")
        } else {
            result = response.let {
                Gson().fromJson(it, clazz)
            }
        }

        return result
    }


    fun parse(response: Call<ResponseBody>, clazz: Class<out Any>): Any {
        val responseBody = response.execute()

        if (responseBody.isSuccessful) {
            val stringValue = responseBody.body()?.string()
            return parse(stringValue, clazz)
        } else {
            val errorResponse = resolveError(responseBody)
            throw ErrorResponseException(errorResponse.status, errorResponse)
        }
    }

    fun parse(response: Call<ResponseBody>): String? {
        val responseBody = response.execute()

        if (responseBody.isSuccessful) {
            return responseBody.body()?.string()
        } else {
            val errorResponse = resolveError(responseBody)
            throw ErrorResponseException(errorResponse.status, errorResponse)
        }
    }

    private fun resolveError(responseBody: Response<ResponseBody>): ErrorResponse {

        var errorResponse = Gson().fromJson(responseBody.errorBody()?.string(), ErrorResponse::class.java)

        if (errorResponse.status.isNullOrEmpty()) {
            errorResponse = ErrorResponse(responseBody.errorBody()!!.string())
        }

        errorResponse.errorCode = responseBody.code()
        errorResponse.success = responseBody.isSuccessful
        return errorResponse
    }

}