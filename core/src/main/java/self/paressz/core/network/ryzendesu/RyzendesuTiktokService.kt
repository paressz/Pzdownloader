package self.paressz.core.network.ryzendesu

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import self.paressz.core.model.ryzendesu.RyzendesuTiktokResponse

interface RyzendesuTiktokService {
    @GET("downloader/ttdl")
    fun downloadTiktokVideo(
        @Query("url") url : String
    ) : Call<RyzendesuTiktokResponse>

}