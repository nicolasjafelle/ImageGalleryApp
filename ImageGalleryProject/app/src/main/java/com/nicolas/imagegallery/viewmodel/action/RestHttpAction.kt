package com.nicolas.imagegallery.viewmodel.action

sealed class RestHttpAction {

    object UnknownError : RestHttpAction()
    object HostUnreachable : RestHttpAction()
    class HttpErrorCode(val errorCode: Int, val message: String) : RestHttpAction()
    object Loading : RestHttpAction()
    object Success : RestHttpAction()


}