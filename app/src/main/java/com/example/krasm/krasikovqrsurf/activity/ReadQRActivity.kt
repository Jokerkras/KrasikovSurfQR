package com.example.krasm.krasikovqrsurf.activity

import android.app.Activity
import android.content.Intent
import android.hardware.Camera
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.SurfaceHolder
import android.view.ViewTreeObserver
import com.example.krasm.krasikovqrsurf.R
import com.example.krasm.krasikovqrsurf.WorkWithImage
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_read_qr.*
import org.jetbrains.anko.browse
import java.io.IOException
import java.util.concurrent.TimeUnit
import android.R.attr.data
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import android.os.Environment.DIRECTORY_PICTURES
import android.os.Environment.getExternalStoragePublicDirectory




class ReadQRActivity : AppCompatActivity(), SurfaceHolder.Callback, Camera.PictureCallback{
    override fun onPictureTaken(p0: ByteArray?, p1: Camera?) {
        camera.startPreview()
    }

    var pictures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    val photoFile = File(pictures, "myphoto.jpg")
    lateinit var surfaceHolder: SurfaceHolder
    lateinit var camera: Camera
    lateinit var vto: ViewTreeObserver
    var disp = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_read_qr)
        vto = surfaceView.viewTreeObserver
        vto.addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener {
            disp.add(Observable.interval(6, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe {camera.takePicture(null, null, null, Camera.PictureCallback { bytes: ByteArray, camera: Camera ->
                        val fos = FileOutputStream(photoFile)
                        fos.write(data)
                        fos.close()
                        Single.just(try {
                            WorkWithImage.sendImages(bytes)
                        } catch (e: Throwable) {
                            e.printStackTrace()
                        })
                    })})
            disp.add(Observable.interval(1,TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe{
                        if (link != "")
                            openLink(link)
            })
        })
        surfaceHolder = surfaceView.holder
        surfaceHolder.addCallback(this)

        camera = Camera.open()
        camera.setDisplayOrientation(90)
        camera.parameters.setPictureSize(camera.parameters.supportedPictureSizes[0].height, camera.parameters.supportedPictureSizes[0].width)
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
            camera.startPreview()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun openLink(link: String) {
        disp.dispose()
        Log.d("my", link)
        camera.stopPreview()
        camera.release()
        //browse(link)
        var dlg = AlertDialog.Builder(this)
                .setTitle(link)
                .setPositiveButton("OK", {dialog, which ->  dialog.cancel()})
                .show()
    }
    companion object {
        var link: String = ""
    }
}