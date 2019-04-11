package com.nicolas.imagegallery.repository

import com.nicolas.imagegallery.BuildConfig
import com.nicolas.imagegallery.api.ApiClient
import com.nicolas.imagegallery.api.request.AuthRequest
import com.nicolas.imagegallery.domain.Picture
import okhttp3.ResponseBody
import retrofit2.Call

object AppRepo {

    private val apiService = ApiClient.instance

    val pictureList: MutableList<Picture> = emptyList<Picture>().toMutableList()
    var selectedPosition: Int? = null

    fun authenticate(): Call<ResponseBody> {
        val request = AuthRequest(BuildConfig.API_KEY)

        return apiService.auth(request)
    }

    fun getImageList(currentPage: Int = 1): Call<ResponseBody> {
        return apiService.getImageList(currentPage)
    }

    fun getImageDetails(pos: Int): Call<ResponseBody> {
        val selectedPicture = pictureList[pos]
        return apiService.getImageDetails(selectedPicture.id)
    }

    fun addAll(newItemlist: MutableList<Picture>) {
        pictureList.addAll(newItemlist)
    }

}