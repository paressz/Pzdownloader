package self.paressz.core.network.ryzendesu

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import self.paressz.core.model.ryzendesu.RyzendesuFbResponse

interface RyzendesuFacebookService {
    @GET("downloader/fbdl")
    fun downloadFacebookVideo(
        @Query("url") url : String
    ): Call<RyzendesuFbResponse>
}
