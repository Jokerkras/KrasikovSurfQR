package com.example.krasm.krasikovqrsurf.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.krasm.krasikovqrsurf.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        create_act_button.setOnClickListener({openCreateActivity()})
        read_act_button.setOnClickListener({openReadActivity()})
    }

    fun openCreateActivity(){
        var Intent = Intent(this, CreateQRActivity::class.java)
        startActivity(Intent)
    }

    fun openReadActivity(){
        var Intent = Intent(this, ReadQRActivity::class.java)
        startActivity(Intent)
    }
}
