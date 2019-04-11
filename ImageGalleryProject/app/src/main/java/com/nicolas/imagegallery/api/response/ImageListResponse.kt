package com.nicolas.imagegallery.api.response

import com.nicolas.imagegallery.domain.Picture

class ImageListResponse() {

    var pictures: List<Picture> = emptyList()

    var page: Int = 1

    var pageCount = 1

    var hasMore = false // By default only one page

}