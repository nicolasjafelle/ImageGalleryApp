package com.nicolas.imagegallery.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicolas.imagegallery.utils.RestHttpExceptionHandler
import com.nicolas.imagegallery.viewmodel.action.RestHttpAction
import com.nicolas.imagegallery.viewmodel.callback.BasicCallback
import com.nicolas.imagegallery.viewmodel.event.Event
import kotlinx.coroutines.*
import java.lang.Exception

open class CoroutineViewModel: ViewModel() {
    lateinit var viewStateData: MutableLiveData<Event<RestHttpAction>>
    var singleCallback: BasicCallback? = null
    var job: Job? = null
    val exceptionHandler = RestHttpExceptionHandler()


    fun executeInBackground(onBackgroundTask: () -> Any,
                            onSuccess: (response: Any?) -> Unit,
                            callback: BasicCallback? = null) {

        singleCallback = callback

        job = CoroutineScope(Dispatchers.Main).launch(exceptionHandler) {
            try {
                val bg = async(Dispatchers.IO) {
                    if (isActive) {
                        onBackgroundTask()
                    } else {
                        return@async null
                    }
                }

                val response = bg.await()
                onSuccess(response)
                callback?.onSuccess()

            } catch (e: Throwable) {
                callback?.onError(e.message)
                exceptionHandler.handle(Dispatchers.Main, e, viewStateData)
            }
        }
    }

    fun simpleBackgroundTask(onBackgroundTask: () -> Unit) {
        job = CoroutineScope(Dispatchers.Main).launch(exceptionHandler) {

            try {
                async(Dispatchers.IO) {
                    if (isActive) {
                        onBackgroundTask()
                    } else {
                        return@async null
                    }
                }
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        singleCallback = null
    }

}