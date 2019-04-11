package com.nicolas.imagegallery.api

import com.nicolas.imagegallery.api.request.AuthRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST(Endpoints.AUTH)
    fun auth(@Body request: AuthRequest): Call<ResponseBody>

    @GET(Endpoints.IMAGES)
    fun getImageList(@Query("page") currentPage: Int): Call<ResponseBody>

    @GET(Endpoints.IMAGE_DETAIL)
    fun getImageDetails(@Path("id") pictureId: String): Call<ResponseBody>

}