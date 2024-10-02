package self.paressz.core.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import self.paressz.core.model.ryzendesu.RyzenDesuFbResponse
import self.paressz.core.model.ryzendesu.RyzenDesuIgResponse
import self.paressz.core.model.ryzendesu.RyzenDesuXResponse
import self.paressz.core.model.ryzendesu.RyzenDesuXResponseAlter
import self.paressz.core.network.ryzendesu.CdnService
import self.paressz.core.network.ryzendesu.FacebookService
import self.paressz.core.network.ryzendesu.InstagramService
import self.paressz.core.network.ryzendesu.XService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadRepository
@Inject constructor(
    private val xService: XService,
    private val instagramService: InstagramService,
    private val facebookService: FacebookService,
    private val cdnService: CdnService
)  {
    val xState = MutableLiveData<LoadState<RyzenDesuXResponse>>()
    fun downloadXVideo(url: String) : LiveData<LoadState<RyzenDesuXResponse>> {
        xState.value = LoadState.Loading
        xService.downloadXVideo(url).enqueue(object : Callback<JsonObject> {
            override fun onResponse(p0: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful && response.body() != null) {
                    val jsonResponse = response.body()!!
                    Log.d("json response x repo", "onResponse: ${jsonResponse.toString()}")
                    jsonResponse.let {
                        val type = it.get("type")?.asString
                        val status = it.get("status")?.asBoolean
                        val msg = it.get("msg")?.asString
                        val medias = it.get("media")?.asJsonArray
                        val mediaItem = mutableListOf<RyzenDesuXResponse.Media>()
                        if(type == "video") {
                            medias?.forEach { media ->
                                val mediaObj = media.asJsonObject
                                val url = mediaObj.get("url").asString
                                val quality = mediaObj.get("quality").asString
                                mediaItem.add(RyzenDesuXResponse.Media.MultiType(url, quality))
                            }
                        } else if (type == "image") {
                            medias?.forEach { media ->
                                val imageUrl = media.asString
                                mediaItem.add(RyzenDesuXResponse.Media.Image(imageUrl))
                            }
                        }
                        if(status != null)
                            xState.value = LoadState.Success(RyzenDesuXResponse(mediaItem, type, status, msg))
                        else
                            xState.value = LoadState.Error("Failed to fetch data : Status is null")
                    }
                } else {
                    xState.value = LoadState.Error(response.message() + response.code())
                }
            }

            override fun onFailure(p0: Call<JsonObject>, t: Throwable) {
                xState.value = LoadState.Error(t.message.toString())
            }
        })
        return xState
    }

    val xStateAlter = MutableLiveData<LoadState<RyzenDesuXResponseAlter>>()
    fun downloadXVideoAlter(url: String) : LiveData<LoadState<RyzenDesuXResponseAlter>> {
        xStateAlter.value = LoadState.Loading
        xService.downloadXVideoAlter(url).enqueue(object :Callback<RyzenDesuXResponseAlter>{
            override fun onResponse(p0: Call<RyzenDesuXResponseAlter>, response: Response<RyzenDesuXResponseAlter>) {
                if(response.isSuccessful) {
                    val body = response.body() as RyzenDesuXResponseAlter
                    xStateAlter.value = LoadState.Success(body)
                } else {
                    xStateAlter.value = LoadState.Error(response.message() + response.code())
                }
            }

            override fun onFailure(p0: Call<RyzenDesuXResponseAlter>, response: Throwable) {
                xStateAlter.value = LoadState.Error(response.message.toString())
            }

        })
        return xStateAlter
    }

    val igState = MutableLiveData<LoadState<RyzenDesuIgResponse>>()
    suspend fun downloadInstagramVideo(url: String) : LiveData<LoadState<RyzenDesuIgResponse>>  {
        igState.value = LoadState.Loading
        instagramService.donwloadInstagramVideo(url) .enqueue(object : Callback<RyzenDesuIgResponse>{
            override fun onResponse(p0: Call<RyzenDesuIgResponse>, response: Response<RyzenDesuIgResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    igState.value = LoadState.Success(body as RyzenDesuIgResponse)
                } else {
                    igState.value = LoadState.Error(response.message() + response.code())
                }
            }

            override fun onFailure(p0: Call<RyzenDesuIgResponse>, response: Throwable) {
                igState.value = LoadState.Error(response.message.toString())
            }
        })
        return igState
    }

    val fbState = MutableLiveData<LoadState<RyzenDesuFbResponse>>()
    fun downloadFacebookVideo(url: String) : LiveData<LoadState<RyzenDesuFbResponse>> {
        fbState.value = LoadState.Loading
        facebookService.downloadFacebookVideo(url).enqueue(object :Callback<RyzenDesuFbResponse>{
            override fun onResponse(p0: Call<RyzenDesuFbResponse>, response: Response<RyzenDesuFbResponse>) {
                if (response.isSuccessful) {
                    val body = response.body() as RyzenDesuFbResponse
                    fbState.value  = LoadState.Success(body)
                } else {
                    fbState.value = LoadState.Error(response.message() + response.code())
                }
            }
            override fun onFailure(p0: Call<RyzenDesuFbResponse>, response: Throwable) {
                fbState.value = LoadState.Error(response.message.toString())
            }
        })
        return fbState
    }

    suspend fun cdnDownloadVideo(url: String) = cdnService.downloadVideo(url).byteStream()
}