package com.nicolas.imagegallery.api.response

import com.google.gson.annotations.SerializedName
import com.nicolas.imagegallery.domain.PictureDetail

data class ImageDetailResponse(val id: String,
                               @SerializedName("cropped_picture") val croppedUrl: String,
                                val author: String,
                                val camera: String,
                                @SerializedName("full_picture") val fullUrl: String) {


    fun toDomain(): PictureDetail {

        return PictureDetail(id, croppedUrl, author, camera, fullUrl)
    }

}