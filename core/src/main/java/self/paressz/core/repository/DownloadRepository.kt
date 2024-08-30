package self.paressz.core.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.coroutineScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import self.paressz.core.model.XResponse
import self.paressz.core.network.CdnService
import self.paressz.core.network.FacebookService
import self.paressz.core.network.InstagramService
import self.paressz.core.network.XService
import self.paressz.core.util.LoadState
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
    val xState = MutableLiveData<LoadState<XResponse.MediaItem>>()
    fun downloadXVideo(url: String) : LiveData<LoadState<XResponse.MediaItem>> {
        xService.downloadXVideo(url).enqueue(object : Callback<XResponse> {
            override fun onResponse(call: Call<XResponse>, response: Response<XResponse>) {
                xState.value = LoadState.Loading
                if (response.isSuccessful) {
                    xState.value = LoadState.Success(response.body()?.media as XResponse.MediaItem)
                } else {
                    xState.value = LoadState.Error(response.body().toString())
                }
            }

            override fun onFailure(p0: Call<XResponse>, p1: Throwable) {
                xState.value = LoadState.Error(p1.toString())
            }
        })
        return xState
    }
    fun downloadXVideoAlter(url: String) {
        xService.downloadXVideoAlter(url)
    }
    fun downloadInstagramVideo(url: String) = instagramService.donwloadInstagramVideo(url)
    fun downloadFacebookVideo(url: String) = facebookService.downloadFacebookVideo(url)

    suspend fun downloadVideo(url: String) = cdnService.downloadVideo(url).byteStream()
}