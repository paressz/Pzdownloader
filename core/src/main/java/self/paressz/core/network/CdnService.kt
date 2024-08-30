package self.paressz.core.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface CdnService {
    @GET
    @Streaming
    suspend fun downloadVideo(@Url videoUrl : String) : ResponseBody
}