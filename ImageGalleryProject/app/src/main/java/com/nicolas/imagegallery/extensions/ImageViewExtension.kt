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

}

fun ImageView.loadImage(url: String?) {
    if (url != null && url.isNotEmpty())

        Glide.with(context)
            .load(url)
            .centerCrop()
            .into(this)

}
