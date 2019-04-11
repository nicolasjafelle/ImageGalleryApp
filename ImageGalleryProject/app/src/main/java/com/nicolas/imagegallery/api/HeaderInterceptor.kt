package com.nicolas.imagegallery.api

import android.util.Log
import com.nicolas.imagegallery.ImageGalleryApplication
import com.nicolas.imagegallery.session.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    private val AUTH_KEY = "Authorization"

    private var authToken: String? = null


    private val sessionManager: SessionManager by lazy {
        SessionManager(ImageGalleryApplication.instance.baseContext)
    }

    override fun intercept(chain: Interceptor.Chain?): Response {

        val request = chain!!.request()

        val requestBuilder = request.newBuilder()
        authToken = sessionManager.getToken()


        authToken?.let {
            requestBuilder.addHeader(AUTH_KEY, "Bearer $it")
        }

        if (authToken == null) {
            Log.i("HEADER_INTERCEPTOR", "TOKEN IS NULL")
        }

        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)

    }

}
