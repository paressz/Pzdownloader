package self.paressz.core.network
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import self.paressz.core.BuildConfig


object RetrofitClient {
    private const val BASE_URL = "https://api.ryzendesu.vip/api/"
    private val loggingInterceptor =
        if(BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
//        .cookieJar(MyCookieJar())
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getXService() = retrofit.create(XService::class.java)
    fun getInstagramService() = retrofit.create(InstagramService::class.java)
    fun getFacebookService() = retrofit.create(FacebookService::class.java)
    fun getCdnService() = retrofit.create(CdnService::class.java)
}
//class MyCookieJar : CookieJar {
//    private val cookieStore: MutableMap<HttpUrl, MutableList<Cookie>> = HashMap()
//
//    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
//        cookieStore[url] = cookies.toMutableList()
//    }
//
//    override fun loadForRequest(url: HttpUrl): List<Cookie> {
//        return cookieStore[url] ?: emptyList()
//    }
//}