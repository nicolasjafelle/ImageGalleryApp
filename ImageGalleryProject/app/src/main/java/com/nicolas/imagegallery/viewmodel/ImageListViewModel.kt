package com.nicolas.imagegallery.viewmodel

import androidx.lifecycle.MutableLiveData
import com.nicolas.imagegallery.api.ResponseParser
import com.nicolas.imagegallery.api.response.ImageListResponse
import com.nicolas.imagegallery.domain.Picture
import com.nicolas.imagegallery.repository.AppRepo

class ImageListViewModel : CoroutineViewModel() {

    val listViewModel = MutableLiveData<MutableList<Picture>>()

    private var currentPage = 1
    private var hasMore = false


    fun onLoadMoreItems() {
        if (hasMore) {
            getImageList()
        }
    }

    fun setSelectedPosition(pos: Int) {
        AppRepo.selectedPosition = pos
    }

    fun getImageList() {
        executeInBackground(
            onBackgroundTask = {
                ResponseParser.parse(
                    AppRepo.getImageList(currentPage),
                    ImageListResponse::class.java
                )

            },
            onSuccess = {
                if (it is ImageListResponse) {
                    it.let { response ->
                        AppRepo.addAll(response.pictures.toMutableList())
                        listViewModel.value = AppRepo.pictureList

                        if (response.hasMore) {
                            currentPage++
                        }
                        hasMore = response.hasMore
                    }
                }
            }
        )

    }

}