package com.example.krasm.krasikovqrsurf

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.krasm.krasikovqrsurf.activity.ReadQRActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream


class WorkWithImage {
    companion object {

        fun sendImages(image: ByteArray) {
            val okHttpClient = OkHttpClient()

            var stream = ByteArrayOutputStream()

            var bitMap = BitmapFactory.decodeByteArray(image, 0, image.size)
            bitMap.compress(Bitmap.CompressFormat.JPEG, 30, stream)

            val header: Map<String, String> = hashMapOf("file" to "file")

            val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), stream.toByteArray())
            val part = MultipartBody.Part.createFormData("file", "file.jpg", requestBody)
            QRApplication.qrRepo.readQR(part)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        result ->
                            if(result[0].symbol[0].data == null) {
                                ReadQRActivity.link = result[0].symbol[0].error!!
                            }
                            else {ReadQRActivity.link = result[0].symbol[0].data!!}
                    }, {error -> error.printStackTrace()})
        }
    }
}