package com.nicolas.imagegallery.utils

import android.app.Activity
import android.content.Intent
import com.nicolas.imagegallery.R
import com.nicolas.imagegallery.domain.PictureDetail

object ActivityLauncher {

    fun launchShareIntent(activity: Activity, pictureDetail: PictureDetail) {

        val text = activity.getString(
            R.string.check_out_this_image_from,
            pictureDetail.author,
            pictureDetail.fullUrl
        )


        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.check_out_this_image))
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text)

        activity.startActivity(
            Intent.createChooser(
                sharingIntent,
                activity.getString(R.string.select_app_to_share)
            )
        )
    }

}