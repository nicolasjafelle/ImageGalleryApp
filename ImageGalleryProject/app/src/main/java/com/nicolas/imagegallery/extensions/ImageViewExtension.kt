package com.nicolas.imagegallery.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nicolas.imagegallery.R

/**
 * Created by nicolas on 11/10/17.
 */


fun ImageView.loadImage(url: String?, placeholder: Int? = null) {
    if (url != null && url.isNotEmpty())

        Glide.with(context)
            .load(url)
            .centerCrop()
            .placeholder(placeholder ?: R.drawable.ic_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(this)

}

fun ImageView.loadImage(url: String?) {
    if (url != null && url.isNotEmpty())

        Glide.with(context)
            .load(url)
            .centerCrop()
            .into(this)

}
