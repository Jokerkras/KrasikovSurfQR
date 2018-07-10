package com.example.krasm.krasikovqrsurf.activity

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.hardware.Camera
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.SurfaceHolder
import android.view.WindowManager
import android.widget.TextView
import com.example.krasm.krasikovqrsurf.R
import com.example.krasm.krasikovqrsurf.network.QRApi
import com.example.krasm.krasikovqrsurf.repo.QRRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_create_qr.*
import kotlinx.android.synthetic.main.activity_read_qr.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Thread.sleep
import java.security.Timestamp
import java.util.concurrent.TimeUnit


class ReadQRActivity : AppCompatActivity(), SurfaceHolder.Callback{

    lateinit var surfaceHolder: SurfaceHolder
    lateinit var camera: Camera


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_qr)

        surfaceHolder = surfaceView.holder
        surfaceHolder.addCallback(this)
        camera = Camera.open()

        camera.setDisplayOrientation(90)
        Log.d("view","created")
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        Log.d("surface","created")
        try {
            camera.setPreviewDisplay(holder)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        camera.startPreview()
    }

    fun sendImages(image: ByteArray) {
        val okHttpClient = OkHttpClient()
        val qrRepo = QRRepo(QRApi.create())

        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), image)
        val part = MultipartBody.Part.createFormData("file", "file", requestBody)
        sleep(1000)
        qrRepo.readQR(part)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    result -> var dlg = AlertDialog.Builder(this)
                        .setTitle(result.toString())
                        .setPositiveButton("OK", {dialog, which ->  dialog.cancel()})
                        .show()
                }, {error -> error.printStackTrace()})
    }
}
