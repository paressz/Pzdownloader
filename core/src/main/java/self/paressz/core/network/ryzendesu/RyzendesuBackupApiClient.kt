package self.paressz.core.network.ryzendesu

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import self.paressz.core.network.OkHttpLogClient

object RyzendesuBackupApiClient {
    private const val BASE_URL = "https://apidl.asepharyana.cloud/api/"

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(OkHttpLogClient.getClient())
        .addConverterFactory(GsonConverterFactory.create()).build()

    @RyzendesuBackupServer fun getXService() = retrofit.create(RyzendesuXService::class.java)
    @RyzendesuBackupServer fun getInstagramService() = retrofit.create(RyzendesuInstagramService::class.java)
    @RyzendesuBackupServer fun getTiktokService() = retrofit.create(RyzendesuTiktokService::class.java)
    @RyzendesuBackupServer fun getFacebookService() = retrofit.create(RyzendesuFacebookService::class.java)
    @RyzendesuBackupServer fun getYoutubeService() = retrofit.create(RyzendesuYoutubeService::class.java)
}