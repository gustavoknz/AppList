package com.kieling.aptoide.dagger

import com.kieling.aptoide.db.AptoideDao
import com.kieling.aptoide.network.AptoideApiService
import com.kieling.aptoide.repository.AptoideRepository
import com.kieling.aptoide.view.AppListFragment
import dagger.Component
import dagger.Module
import dagger.Provides

@PerFragment
@Component(dependencies = [AppComponent::class], modules = [AptoideRepositoryModule::class])
interface FragmentComponent {
    fun inject(fragment: AppListFragment)
    fun aptoideRepository(): AptoideRepository
}

@Module
class AptoideRepositoryModule {

    @Provides
    @PerFragment
    fun provideAptoideRepository(
        aptoideDao: AptoideDao,
        aptoideApiService: AptoideApiService
    ): AptoideRepository {
        return AptoideRepository(aptoideDao, aptoideApiService)
    }
}