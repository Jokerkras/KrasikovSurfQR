package com.example.krasm.krasikovqrsurf.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.krasm.krasikovqrsurf.R
import com.example.krasm.krasikovqrsurf.network.INetwork
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.jetbrains.anko.browse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ReadQRActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_qr)

        val okHttpClient = OkHttpClient()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.qrserver.com/v1/")
                .client(okHttpClient)
                .build()

        val service: INetwork = retrofit.create(INetwork::class.java)

        /*val call: Call<ResponseBody> = service.uploadQR()

        try {
            call.enqueue(object: Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    Log.d("fail",t.toString())
                }

                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                }

            })
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }*/
    }
}
