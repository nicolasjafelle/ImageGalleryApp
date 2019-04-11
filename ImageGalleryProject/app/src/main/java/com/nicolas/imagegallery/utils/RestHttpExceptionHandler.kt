package com.nicolas.imagegallery.utils


import androidx.lifecycle.MutableLiveData
import com.nicolas.imagegallery.ImageGalleryApplication
import com.nicolas.imagegallery.viewmodel.action.RestHttpAction
import com.nicolas.imagegallery.viewmodel.event.Event
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 * Created by nicolas on 11/30/17.
 */
class RestHttpExceptionHandler : CoroutineExceptionHandler, AbstractCoroutineContextElement(CoroutineExceptionHandler.Key) {


    override fun handleException(context: CoroutineContext, exception: Throwable) {
        if (exception is CancellationException) return
        if (context[Job]?.isCancelled == false) return
//        else exception.printStackTrace()
    }

    fun handle(context: CoroutineContext, exception: Throwable, liveData: MutableLiveData<Event<RestHttpAction>>) {
        handleException(context, exception)
        onError(exception, liveData)
    }


    private fun onError(throwable: Throwable?, liveData: MutableLiveData<Event<RestHttpAction>>) {
        when (throwable) {
            is ErrorResponseException -> {
                try {
                    val message = throwable.errorResponse.status
                    val errorCode = throwable.errorResponse.errorCode

                    if (errorCode == 401 && ImageGalleryApplication.instance.isLogged()) {
                        ImageGalleryApplication.instance.logout()
                    } else {
                        onHttpErrorCode(errorCode, message, liveData)
                    }

                } catch (exc: Exception) {
                    onUnknownError(exc, liveData)
                }
            }
            is HttpException -> { //Just in case We are not handling everything in ErrorResponseException
                try {
                    val response = throwable.response().errorBody()?.string()
                    val errorCode = throwable.code()

                    if (errorCode == 401 && ImageGalleryApplication.instance.isLogged()) {
                        ImageGalleryApplication.instance.logout()
                    } else {
                        onHttpErrorCode(errorCode, response, liveData)
                    }

                } catch (exc: Exception) {
                    onUnknownError(exc, liveData)
                }
            }
            is UnknownHostException -> onHostUnreachable(liveData)
            is ConnectException -> onHostUnreachable(liveData)
            else -> {
                if (throwable != null) {
                    onUnknownError(throwable, liveData)
                }
            }
        }
    }

    private fun onUnknownError(e: Throwable, liveData: MutableLiveData<Event<RestHttpAction>>) {
        if (e !is CancellationException) {
            e.printStackTrace()
            liveData.value = Event(RestHttpAction.UnknownError)
        }
    }

    private fun onHostUnreachable(liveData: MutableLiveData<Event<RestHttpAction>>) {
        liveData.value = Event(RestHttpAction.HostUnreachable)
    }

    private fun onHttpErrorCode(errorCode: Int, response: String?, liveData: MutableLiveData<Event<RestHttpAction>>) {
        val restError = RestHttpAction.HttpErrorCode(errorCode, response!!)
        liveData.value = Event(restError)
    }


}