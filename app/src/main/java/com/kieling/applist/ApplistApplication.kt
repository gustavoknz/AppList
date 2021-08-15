package com.kieling.applist

import android.app.Application
import com.kieling.applist.dagger.*

class ApplistApplication: Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .applistApiModule(ApplistApiModule())
            .applistModule(ApplistModule(this))
            .applistDaoModule(ApplistDaoModule(this))
            .build()
    }
}