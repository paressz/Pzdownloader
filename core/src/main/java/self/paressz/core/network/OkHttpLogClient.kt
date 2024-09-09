package self.paressz.core.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import self.paressz.core.BuildConfig
import self.paressz.core.network.ryzendesu.MyCookieJar

object OkHttpLogClient {
    private val loggingInterceptor =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)

    fun getClient() =
        OkHttpClient.Builder().addInterceptor(loggingInterceptor).cookieJar(MyCookieJar()).build()
}