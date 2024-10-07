package self.paressz.core.network.ryzendesu

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import self.paressz.core.network.OkHttpLogClient


object RyzendesuApiClient {
    private const val BASE_URL = "https://api.ryzendesu.vip/api/"

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(OkHttpLogClient.getClient())
        .addConverterFactory(GsonConverterFactory.create()).build()

    fun getXService() = retrofit.create(RyzendesuXService::class.java)
    fun getInstagramService() = retrofit.create(RyzendesuInstagramService::class.java)
    fun getTiktokService() = retrofit.create(RyzendesuTiktokService::class.java)
    fun getFacebookService() = retrofit.create(RyzendesuFacebookService::class.java)
}

class MyCookieJar : CookieJar {
    private val cookieStore: MutableMap<HttpUrl, MutableList<Cookie>> = HashMap()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStore[url] = cookies.toMutableList()
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStore[url] ?: emptyList()
    }
}