package self.paressz.core.network.ryzendesu

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import self.paressz.core.model.ryzendesu.RyzendesuYtResponse

interface RyzendesuYoutubeService {
    @GET("downloader/ytmp4")
    fun downloadYoutubeVideo(
        @Query("url")
        url : String,
        @Query("reso")
        videoRes : String?
    ) : Call<RyzendesuYtResponse>
}