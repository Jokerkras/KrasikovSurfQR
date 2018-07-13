package com.example.krasm.krasikovqrsurf.repo

import com.example.krasm.krasikovqrsurf.data.FirstJSON
import com.example.krasm.krasikovqrsurf.network.QRApi
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.ResponseBody

class QRRepo(val qrApi: QRApi) {
    fun createQR(text: String): Observable<ResponseBody> {
        return qrApi.getQR(text, "400x400")
    }

    fun readQR(file: MultipartBody.Part): Observable<Array<FirstJSON>> {
        return qrApi.uploadQR(file)
    }
}