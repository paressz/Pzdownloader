package self.paressz.core.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import self.paressz.core.network.ryzendesu.CdnService
import self.paressz.core.network.ryzendesu.FacebookService
import self.paressz.core.network.ryzendesu.InstagramService
import self.paressz.core.network.ryzendesu.RyzendesuApiClient
import self.paressz.core.network.ryzendesu.XService
import self.paressz.core.repository.DownloadRepository

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideXService() = RyzendesuApiClient.getXService()
    @Provides
    fun provideInstagramService() = RyzendesuApiClient.getInstagramService()
    @Provides
    fun provideFacebookService() = RyzendesuApiClient.getFacebookService()
    @Provides
    fun provideCdnService() = RyzendesuApiClient.getCdnService()
    @Provides
    fun provideDownloadRepository(
        xService: XService,
        instagramService: InstagramService,
        facebookService: FacebookService,
        cdnService: CdnService
    ) = DownloadRepository(xService, instagramService, facebookService, cdnService)
}