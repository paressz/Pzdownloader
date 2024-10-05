package self.paressz.core.network.ryzendesu

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import self.paressz.core.model.ryzendesu.RyzenDesuXResponseAlter

interface RyzendesuXService {
    @Headers("Accept: application/json")
    @GET("downloader/twitter")
    fun downloadXVideo(
        @Query("url") url : String
    ) : Call<JsonObject>

    @GET("downloader/twitter2")
    fun downloadXVideoAlter(
        @Query("url") url : String
    ) : Call<RyzenDesuXResponseAlter>
}