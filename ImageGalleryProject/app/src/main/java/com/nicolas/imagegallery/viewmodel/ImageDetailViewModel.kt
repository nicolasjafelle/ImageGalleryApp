package com.nicolas.imagegallery.viewmodel

import androidx.lifecycle.MutableLiveData
import com.nicolas.imagegallery.api.ResponseParser
import com.nicolas.imagegallery.api.response.ImageDetailResponse
import com.nicolas.imagegallery.domain.PictureDetail
import com.nicolas.imagegallery.repository.AppRepo

class ImageDetailViewModel : CoroutineViewModel() {
    val detailViewModel = MutableLiveData<PictureDetail>()


    fun getImageDetail(pos: Int) {
        executeInBackground(
            onBackgroundTask = {
                ResponseParser.parse(
                    AppRepo.getImageDetails(pos),
                    ImageDetailResponse::class.java
                )
            },
            onSuccess = {
                if (it is ImageDetailResponse) {
                    it.let { response ->
                        detailViewModel.value = response.toDomain()
                    }
                }
            })

    }

}