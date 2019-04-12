package com.nicolas.imagegallery.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.nicolas.imagegallery.api.ResponseParser
import com.nicolas.imagegallery.api.response.AuthResponse
import com.nicolas.imagegallery.repository.AppRepo
import com.nicolas.imagegallery.session.SessionManager
import com.nicolas.imagegallery.viewmodel.event.Event

class AuthViewModel : CoroutineViewModel() {

    val authLiveData: MutableLiveData<Event<AuthResponse>> = MutableLiveData()

    fun authenticate() {

        executeInBackground(
            onBackgroundTask = {
                ResponseParser.parse(
                    AppRepo.authenticate(),
                    AuthResponse::class.java
                )
            },
            onSuccess = {
                if (it is AuthResponse) {
                    it.let { response ->
                        authLiveData.value = Event(response)
                    }
                }
            })

    }

    fun storeCredentials(context: Context, token: String): Boolean {
        SessionManager(context).saveToken(token)
        return true
    }

}