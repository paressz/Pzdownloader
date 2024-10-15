package self.paressz.core.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import self.paressz.core.network.github.GithubApiClient
import self.paressz.core.network.github.GithubService
import self.paressz.core.network.ryzendesu.RyzendesuBackupServer
import self.paressz.core.network.ryzendesu.RyzendesuMainServer
import self.paressz.core.network.ryzendesu.RyzendesuFacebookService
import self.paressz.core.network.ryzendesu.RyzendesuInstagramService
import self.paressz.core.network.ryzendesu.RyzendesuApiClient
import self.paressz.core.network.ryzendesu.RyzendesuBackupApiClient
import self.paressz.core.network.ryzendesu.RyzendesuTiktokService
import self.paressz.core.network.ryzendesu.RyzendesuXService
import self.paressz.core.repository.github.GithubRepository
import self.paressz.core.repository.ryzendesu.RyzendesuDownloadRepository

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @RyzendesuMainServer
    fun provideRyzendesuXService() = RyzendesuApiClient.getXService()
    @Provides
    @RyzendesuMainServer
    fun provideRyzendesuInstagramService() = RyzendesuApiClient.getInstagramService()
    @Provides
    @RyzendesuMainServer
    fun provideRyzendesuFacebookService() = RyzendesuApiClient.getFacebookService()
    @Provides
    @RyzendesuMainServer
    fun provideRyzendesuTiktokService() = RyzendesuApiClient.getTiktokService()
    @Provides
    @RyzendesuBackupServer
    fun provideRyzendesuBackupXService() = RyzendesuBackupApiClient.getXService()
    @Provides
    @RyzendesuBackupServer
    fun provideRyzendesuBackupInstagramService() = RyzendesuBackupApiClient.getInstagramService()
    @Provides
    @RyzendesuBackupServer
    fun provideRyzendesuBackupFacebookService() = RyzendesuBackupApiClient.getFacebookService()
    @Provides
    @RyzendesuBackupServer
    fun provideRyzendesuBackupTiktokService() = RyzendesuBackupApiClient.getTiktokService()
    @Provides
    fun provideGithubService() = GithubApiClient.getGithubService()

    @Provides
    fun provideDownloadRepository(
        @RyzendesuMainServer ryzendesuXService: RyzendesuXService,
        @RyzendesuMainServer ryzendesuInstagramService: RyzendesuInstagramService,
        @RyzendesuMainServer ryzendesuFacebookService: RyzendesuFacebookService,
        @RyzendesuMainServer ryzendesuTiktokService: RyzendesuTiktokService,
        @RyzendesuBackupServer ryzendesuBackupXService: RyzendesuXService,
        @RyzendesuBackupServer ryzendesuBackupInstagramService: RyzendesuInstagramService,
        @RyzendesuBackupServer ryzendesuBackupFacebookService: RyzendesuFacebookService,
        @RyzendesuBackupServer ryzendesuBackupTiktokService: RyzendesuTiktokService
    ) = RyzendesuDownloadRepository(
        ryzendesuXService, ryzendesuInstagramService, ryzendesuFacebookService, ryzendesuTiktokService,
        ryzendesuBackupXService, ryzendesuBackupInstagramService, ryzendesuBackupFacebookService, ryzendesuBackupTiktokService,
    )

    @Provides
    fun provideGithubRepository(githubService: GithubService) = GithubRepository(githubService)
}