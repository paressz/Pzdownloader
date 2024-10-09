package self.paressz.core.network.github

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import self.paressz.core.network.OkHttpLogClient

object GithubApiClient {
    private const val BASE_URL = "https://api.github.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpLogClient.getClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getGithubService() = retrofit.create(GithubService::class.java)
}