package self.paressz.core.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import self.paressz.core.repository.DownloadRepository

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideXService() = RetrofitClient.getXService()
    @Provides
    fun provideInstagramService() = RetrofitClient.getInstagramService()
    @Provides
    fun provideFacebookService() = RetrofitClient.getFacebookService()
    @Provides
    fun provideCdnService() = RetrofitClient.getCdnService()
    @Provides
    fun provideDownloadRepository(
        xService: XService,
        instagramService: InstagramService,
        facebookService: FacebookService,
        cdnService: CdnService
    ) = DownloadRepository(xService, instagramService, facebookService, cdnService)
}