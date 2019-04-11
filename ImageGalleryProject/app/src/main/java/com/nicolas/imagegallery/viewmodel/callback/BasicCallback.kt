package com.nicolas.imagegallery.viewmodel.callback


interface BasicCallback {
    fun onSuccess()
    fun onSuccess(result: Any)
    fun onError(error: String? = null)
}