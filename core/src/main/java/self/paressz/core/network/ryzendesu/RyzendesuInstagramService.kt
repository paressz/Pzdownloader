package self.paressz.core.network.ryzendesu

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import self.paressz.core.model.ryzendesu.RyzenDesuIgResponse

interface RyzendesuInstagramService {
    @GET("downloader/igdl")
    fun donwloadInstagramVideo(
        @Query("url") url : String
    ) : Call<RyzenDesuIgResponse>
}
