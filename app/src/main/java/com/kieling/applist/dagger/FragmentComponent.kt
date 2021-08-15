package com.kieling.applist.dagger

import com.kieling.applist.db.ApplistDao
import com.kieling.applist.network.ApplistApiService
import com.kieling.applist.repository.ApplistRepository
import com.kieling.applist.view.AppListFragment
import dagger.Component
import dagger.Module
import dagger.Provides

@PerFragment
@Component(dependencies = [AppComponent::class], modules = [ApplistRepositoryModule::class])
interface FragmentComponent {
    fun inject(fragment: AppListFragment)
    fun applistRepository(): ApplistRepository
}

@Module
class ApplistRepositoryModule {

    @Provides
    @PerFragment
    fun provideApplistRepository(
        applistDao: ApplistDao,
        applistApiService: ApplistApiService
    ): ApplistRepository {
        return ApplistRepository(applistDao, applistApiService)
    }
}