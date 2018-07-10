package com.example.krasm.krasikovqrsurf.network

import com.example.krasm.krasikovqrsurf.data.FirstJSON
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.xml.transform.Result


interface QRApi {
    @GET("create-qr-code/")
    fun getQR(@Query("data") dataString: String,@Query("size") sizeString: String): Observable<ResponseBody>

    @Multipart
    @POST("create-qr-code/")
    fun uploadQR(@Part file: MultipartBody.Part): Observable<FirstJSON>

    companion object Factory {

        fun create(): QRApi {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.qrserver.com/v1/")
                    .build()

            return retrofit.create(QRApi::class.java)
        }
    }
}