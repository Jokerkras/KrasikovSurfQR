package com.example.krasm.krasikovqrsurf.activity

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.krasm.krasikovqrsurf.R
import kotlinx.android.synthetic.main.activity_show_qr.*

class ShowQRActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_qr)

        qr_view.setImageBitmap(Bitmap.createBitmap(intent.getParcelableExtra("code")))
    }
}
