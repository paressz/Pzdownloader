package self.paressz.core

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import self.paressz.core.model.XResponse
import self.paressz.core.model.XResponseAlter

interface XService {
    @GET("/downloader/twitter")
    fun downloadXVideo(
        @Query("url") url : String
    ) : Call<XResponse>
    @GET("/downloader/twitter2")
    fun downloadXVideoAlter(
        @Query("url") url : String
    ) : Call<XResponseAlter>
}
