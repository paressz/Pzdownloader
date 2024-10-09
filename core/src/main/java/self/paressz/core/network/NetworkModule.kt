package self.paressz.core.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import self.paressz.core.network.github.GithubApiClient
import self.paressz.core.network.github.GithubService
import self.paressz.core.network.ryzendesu.RyzendesuFacebookService
import self.paressz.core.network.ryzendesu.RyzendesuInstagramService
import self.paressz.core.network.ryzendesu.RyzendesuApiClient
import self.paressz.core.network.ryzendesu.RyzendesuTiktokService
import self.paressz.core.network.ryzendesu.RyzendesuXService
import self.paressz.core.repository.github.GithubRepository
import self.paressz.core.repository.ryzendesu.RyzendesuDownloadRepository

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideRyzendesuXService() = RyzendesuApiClient.getXService()
    @Provides
    fun provideRyzendesuInstagramService() = RyzendesuApiClient.getInstagramService()
    @Provides
    fun provideRyzendesuFacebookService() = RyzendesuApiClient.getFacebookService()
    @Provides
    fun provideRyzendesuTiktokService() = RyzendesuApiClient.getTiktokService()
    @Provides
    fun provideGithubService() = GithubApiClient.getGithubService()

    @Provides
    fun provideDownloadRepository(
        ryzendesuXService: RyzendesuXService,
        ryzendesuInstagramService: RyzendesuInstagramService,
        ryzendesuFacebookService: RyzendesuFacebookService,
        ryzendesuTiktokService: RyzendesuTiktokService
    ) = RyzendesuDownloadRepository(ryzendesuXService, ryzendesuInstagramService, ryzendesuFacebookService, ryzendesuTiktokService)

    @Provides
    fun provideGithubRepository(githubService: GithubService) = GithubRepository(githubService)
}