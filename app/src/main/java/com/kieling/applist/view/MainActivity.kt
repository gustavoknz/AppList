package com.kieling.applist.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kieling.applist.ApplistApplication
import com.kieling.applist.R

class MainActivity : AppCompatActivity() {
    val appComponent by lazy {
        (application as ApplistApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            appComponent.inject(this)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}