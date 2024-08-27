package self.paressz.core

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import self.paressz.core.model.TtResponse

interface TikTokService {
    @GET("/downloader/ttdl")
    fun downloadTiktokVideo(
        @Query("url") url : String
    ) : Call<TtResponse>
}
