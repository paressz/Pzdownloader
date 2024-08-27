package self.paressz.core

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import self.paressz.core.model.IgResponse

interface InstagramService {
    @GET("/downloader/igdl")
    fun donwloadInstagramVideo(
        @Query("url") url : String
    ) : Call<IgResponse>
}
