package com.kieling.applist.dagger

import androidx.room.Room
import com.kieling.applist.ApplistApplication
import com.kieling.applist.BuildConfig.BASE_URL
import com.kieling.applist.view.MainActivity
import com.kieling.applist.db.ApplistDao
import com.kieling.applist.db.ApplistDatabase
import com.kieling.applist.network.ApplistApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplistModule::class,
        ApplistApiModule::class,
        ApplistDaoModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun context(): ApplistApplication
    fun applistApiService(): ApplistApiService
    fun applistDao(): ApplistDao
}

@Module
class ApplistModule(private val context: ApplistApplication) {
    @Provides
    @Singleton
    fun provideContext() = context
}

@Module
class ApplistApiModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideApplistApiService(retrofit: Retrofit): ApplistApiService {
        return retrofit.create(ApplistApiService::class.java)
    }
}

@Module
class ApplistDaoModule(private val context: ApplistApplication) {

    @Provides
    @Singleton
    fun provideApplistDataBase(): ApplistDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ApplistDatabase::class.java,
            "applist_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideApplistDao(applistDatabase: ApplistDatabase): ApplistDao {
        return applistDatabase.getApplistDao()
    }
}