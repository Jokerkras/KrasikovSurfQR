package com.example.krasm.krasikovqrsurf.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.ImageView
import com.example.krasm.krasikovqrsurf.R
import com.example.krasm.krasikovqrsurf.network.INetwork
import kotlinx.android.synthetic.main.activity_create_qr.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream

class CreateQRActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_qr)
        create_button.setOnClickListener({downloadQRCode()})
    }

    fun showResponse(response: Response<ResponseBody>?){
        var inst: InputStream = response?.body()!!.byteStream()
        var bmap: Bitmap = BitmapFactory.decodeStream(inst)

        var imageView = ImageView(this)
        imageView.setImageBitmap(bmap)

        var dlg = AlertDialog.Builder(this)
                .setView(imageView)
                .setTitle("You'r QR code")
                .show()
    }

    fun downloadQRCode(){
        val okHttpClient = OkHttpClient()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.qrserver.com/v1/")
                .client(okHttpClient)
                .build()

        val service: INetwork = retrofit.create(INetwork::class.java)

        val call: Call<ResponseBody> = service.getQR(content_for_qr.text.toString(), "400x400")

        try {
            call.enqueue(object: Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    Log.d("fail",t.toString())
                }

                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                    showResponse(response)
                }

            })
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }
}
