package self.paressz.core.network.ryzendesu

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import self.paressz.core.network.OkHttpLogClient


object RyzendesuApiClient {
    private const val BASE_URL = "https://api.ryzendesu.vip/api/"

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(OkHttpLogClient.getClient())
        .addConverterFactory(GsonConverterFactory.create()).build()

    @RyzendesuMainServer fun getXService() = retrofit.create(RyzendesuXService::class.java)
    @RyzendesuMainServer fun getInstagramService() = retrofit.create(RyzendesuInstagramService::class.java)
    @RyzendesuMainServer fun getTiktokService() = retrofit.create(RyzendesuTiktokService::class.java)
    @RyzendesuMainServer fun getFacebookService() = retrofit.create(RyzendesuFacebookService::class.java)
}

