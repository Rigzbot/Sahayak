package com.rishik.sahayak.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rishik.sahayak.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val extras = intent.extras
        var userType: String? = null
        if (extras != null) {
            userType = extras.getString("userType")
        }
    }
}