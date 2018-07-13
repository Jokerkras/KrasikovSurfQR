package com.example.krasm.krasikovqrsurf.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.example.krasm.krasikovqrsurf.QRApplication
import com.example.krasm.krasikovqrsurf.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_create_qr.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import java.io.InputStream

class CreateQRActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_qr)
        create_button.setOnClickListener({downloadQRCode()})
    }

    fun showResponse(response: ResponseBody){
        var inst: InputStream = response.byteStream()
        var bmap: Bitmap = BitmapFactory.decodeStream(inst)

        var imageView = ImageView(this)
        imageView.setImageBitmap(bmap)

        var dlg = AlertDialog.Builder(this)
                .setView(imageView)
                .setTitle("You'r QR code")
                .setPositiveButton("OK", {dialog, which ->  dialog.cancel()})
                .show()
    }

    fun downloadQRCode(){
        val okHttpClient = OkHttpClient()

        QRApplication.qrRepo.createQR(content_for_qr.text.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    result -> showResponse(result)
                }, {error -> error.printStackTrace()})
    }
}
