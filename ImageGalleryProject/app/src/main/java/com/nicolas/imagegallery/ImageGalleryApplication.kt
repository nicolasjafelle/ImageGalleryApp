package com.nicolas.imagegallery

import android.app.Application
import com.nicolas.imagegallery.session.SessionManager

class ImageGalleryApplication: Application() {

    companion object {
        lateinit var instance: ImageGalleryApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun isLogged(): Boolean {
        return SessionManager(baseContext).getToken() != null
    }

    fun logout(): Boolean {
        SessionManager(baseContext).clear()
        return true
    }



}