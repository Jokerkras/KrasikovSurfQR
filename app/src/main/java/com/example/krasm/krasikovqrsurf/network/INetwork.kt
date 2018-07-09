package com.example.krasm.krasikovqrsurf.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface INetwork {
    @GET("create-qr-code/")
    fun getQR(@Query("data") dataString: String,@Query("size") sizeString: String): Call<ResponseBody>

    @POST("create-qr-code/")
    fun uploadQR(@Part image: MultipartBody.Part): Call<ResponseBody>
}