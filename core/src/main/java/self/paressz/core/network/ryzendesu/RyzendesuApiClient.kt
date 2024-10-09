package self.paressz.core.network.ryzendesu

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import self.paressz.core.network.OkHttpLogClient


object RyzendesuApiClient {
    private const val BASE_URL = "https://api.ryzendesu.vip/api/"

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(OkHttpLogClient.getClient())
        .addConverterFactory(GsonConverterFactory.create()).build()

    fun getXService() = retrofit.create(RyzendesuXService::class.java)
    fun getInstagramService() = retrofit.create(RyzendesuInstagramService::class.java)
<<<<<<< HEAD
    fun getTiktokService() = retrofit.create(RyzendesuTiktokService::class.java)
=======
>>>>>>> master
    fun getFacebookService() = retrofit.create(RyzendesuFacebookService::class.java)
}

