package com.nicolas.imagegallery.utils

import com.nicolas.imagegallery.api.response.ErrorResponse

class ErrorResponseException(message: String?, error: ErrorResponse) : Exception(message) {


    val errorResponse: ErrorResponse = error


}