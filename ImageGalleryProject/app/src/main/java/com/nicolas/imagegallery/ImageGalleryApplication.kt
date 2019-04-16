package com.nicolas.imagegallery

import android.app.Application
import android.content.Intent
import androidx.core.app.ActivityCompat
import com.nicolas.imagegallery.session.SessionManager
import com.nicolas.imagegallery.ui.activity.StarterActivity

class ImageGalleryApplication : Application() {

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

        val intent = Intent(baseContext, StarterActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

        ActivityCompat.startActivity(
            baseContext,
            intent,
            null
        )
        return true
    }


}