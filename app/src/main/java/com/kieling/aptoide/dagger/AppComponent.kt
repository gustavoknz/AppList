package com.kieling.aptoide.dagger

import androidx.room.Room
import com.kieling.aptoide.AptoideApplication
import com.kieling.aptoide.view.MainActivity
import com.kieling.aptoide.db.AptoideDao
import com.kieling.aptoide.db.AptoideDatabase
import com.kieling.aptoide.network.AptoideApiService
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
        AptoideModule::class,
        AptoideApiModule::class,
        AptoideDaoModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun context(): AptoideApplication
    fun aptoideApiService(): AptoideApiService
    fun aptoideDao(): AptoideDao
}

@Module
class AptoideModule(private val context: AptoideApplication) {
    @Provides
    @Singleton
    fun provideContext() = context
}

@Module
class AptoideApiModule {

    companion object {
        const val BASE_URL = "https://ws2.aptoide.com/api/6/bulkRequest/api_list/"
    }

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
    fun provideAptoideApiService(retrofit: Retrofit): AptoideApiService {
        return retrofit.create(AptoideApiService::class.java)
    }
}

@Module
class AptoideDaoModule(private val context: AptoideApplication) {

    @Provides
    @Singleton
    fun provideAptoideDataBase(): AptoideDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AptoideDatabase::class.java,
            "aptoide_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideAptoideDao(aptoideDatabase: AptoideDatabase): AptoideDao {
        return aptoideDatabase.getAptoideDao()
    }
}