package com.kieling.aptoide

import android.app.Application
import com.kieling.aptoide.dagger.*

class AptoideApplication: Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .aptoideApiModule(AptoideApiModule())
            .aptoideModule(AptoideModule(this))
            .aptoideDaoModule(AptoideDaoModule(this))
            .build()
    }
}