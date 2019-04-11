package com.nicolas.imagegallery.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.nicolas.imagegallery.R
import java.io.File

/**
 * Created by nicolas on 11/10/17.
 */


fun ImageView.loadImage(url: String?, placeholder: Int? = null) {
    if (url != null && url.isNotEmpty())

        Glide.with(context)
            .load(url)
            .centerCrop()
            .placeholder(placeholder ?: R.drawable.ic_placeholder)
            .into(this)


//        Picasso.get()
//            .load(url)
//            .fit()
//            .centerCrop()
//            .noFade()
//            .placeholder(placeholder ?: R.drawable.ic_placeholder)
//            .error(placeholder ?: R.drawable.ic_placeholder)
//            .into(this)
}

//fun ImageView.loadImageFile(localPath: String?, placeholder: Int? = null) {
//    if (localPath != null)
//        Picasso.get()
//            .load(File(localPath))
//            .fit()
//            .centerCrop()
//            .error(placeholder ?: R.drawable.ic_placeholder)
//            .into(this)
//}
//
//fun ImageView.loadImage(url: String, downloaded: () -> Unit) {
//
//    val creator = Picasso.get()
//        .load(url)
//        .fit()
//        .centerCrop()
//        .noFade()
//
//    creator.into(this, object : Callback {
//        override fun onSuccess() {
//            downloaded()
//        }
//
//        override fun onError(e: Exception?) {
//            downloaded()
//        }
//    })
//
//}
