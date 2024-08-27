package self.paressz.core
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import self.paressz.core.BuildConfig
object RetrofitClient {
    private const val BASE_URL = "https://api.ryzendesu.vip/api"
    private val loggingInterceptor =
        if(BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getTikTokService() = retrofit.create(TikTokService::class.java)
    fun getInstagramService() = retrofit.create(InstagramService::class.java)
    fun getFacebookService() = retrofit.create(FacebookService::class.java)

}