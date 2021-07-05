package com.kieling.aptoide.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kieling.aptoide.AptoideApplication
import com.kieling.aptoide.R

class MainActivity : AppCompatActivity() {
    val appComponent by lazy {
        (application as AptoideApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            appComponent.inject(this)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}