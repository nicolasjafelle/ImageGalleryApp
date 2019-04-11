package com.nicolas.imagegallery.api.response

/**
 * {"status":"Unauthorized"}
 */
data class ErrorResponse(val status: String) {

    var errorCode = 0
    var success = false
}

