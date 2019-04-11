package com.nicolas.imagegallery.domain

import com.google.gson.annotations.SerializedName

class PictureDetail(id: String,
                    croppedUrl: String,
                    val author: String,
                    val camera: String,
                    @SerializedName("full_picture") val fullUrl: String): Picture(id, croppedUrl)