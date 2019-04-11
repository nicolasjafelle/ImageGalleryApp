package com.nicolas.imagegallery.domain

import com.google.gson.annotations.SerializedName

open class Picture(val id: String,
                   @SerializedName("cropped_picture") val croppedUrl: String)