package self.paressz.core.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        xService.downloadXVideo(url).enqueue(object : Callback<RyzenDesuXResponse> {
            override fun onResponse(call: Call<RyzenDesuXResponse>, response: Response<RyzenDesuXResponse>) {
                if (response.isSuccessful) {
                    xState.value = LoadState.Success(response.body() as RyzenDesuXResponse)
                } else {
                    xState.value = LoadState.Error(response.message() + response.code())
                }
            }

            override fun onFailure(p0: Call<RyzenDesuXResponse>, response: Throwable) {
                xState.value = LoadState.Error(response.message.toString())
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