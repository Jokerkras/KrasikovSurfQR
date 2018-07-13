package com.example.krasm.krasikovqrsurf

import android.app.Application
import com.example.krasm.krasikovqrsurf.network.QRApi
import com.example.krasm.krasikovqrsurf.repo.QRRepo

class QRApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        qrRepo = QRRepo(QRApi.create())
    }

    companion object {
        lateinit var qrRepo: QRRepo
    }
}