package self.paressz.core.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import self.paressz.core.model.FbResponse
import self.paressz.core.model.IgResponse
import self.paressz.core.model.XResponse
import self.paressz.core.model.XResponseAlter
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
    val xState = MutableLiveData<LoadState<XResponse>>()
    fun downloadXVideo(url: String) : LiveData<LoadState<XResponse>> {
        xState.value = LoadState.Loading
        xService.downloadXVideo(url).enqueue(object : Callback<XResponse> {
            override fun onResponse(call: Call<XResponse>, response: Response<XResponse>) {
                if (response.isSuccessful) {
                    xState.value = LoadState.Success(response.body() as XResponse)
                } else {
                    xState.value = LoadState.Error(response.message() + response.code())
                }
            }

            override fun onFailure(p0: Call<XResponse>, response: Throwable) {
                xState.value = LoadState.Error(response.message.toString())
            }
        })
        return xState
    }

    val xStateAlter = MutableLiveData<LoadState<XResponseAlter>>()
    fun downloadXVideoAlter(url: String) : LiveData<LoadState<XResponseAlter>> {
        xStateAlter.value = LoadState.Loading
        xService.downloadXVideoAlter(url).enqueue(object :Callback<XResponseAlter>{
            override fun onResponse(p0: Call<XResponseAlter>, response: Response<XResponseAlter>) {
                if(response.isSuccessful) {
                    val body = response.body() as XResponseAlter
                    xStateAlter.value = LoadState.Success(body)
                } else {
                    xStateAlter.value = LoadState.Error(response.message() + response.code())
                }
            }

            override fun onFailure(p0: Call<XResponseAlter>, response: Throwable) {
                xStateAlter.value = LoadState.Error(response.message.toString())
            }

        })
        return xStateAlter
    }

    val igState = MutableLiveData<LoadState<IgResponse>>()
    suspend fun downloadInstagramVideo(url: String) : LiveData<LoadState<IgResponse>>  {
        igState.value = LoadState.Loading
        instagramService.donwloadInstagramVideo(url) .enqueue(object : Callback<IgResponse>{
            override fun onResponse(p0: Call<IgResponse>, response: Response<IgResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    igState.value = LoadState.Success(body as IgResponse)
                } else {
                    igState.value = LoadState.Error(response.message() + response.code())
                }
            }

            override fun onFailure(p0: Call<IgResponse>, response: Throwable) {
                igState.value = LoadState.Error(response.message.toString())
            }
        })
        return igState
    }

    val fbState = MutableLiveData<LoadState<FbResponse>>()
    fun downloadFacebookVideo(url: String) : LiveData<LoadState<FbResponse>> {
        fbState.value = LoadState.Loading
        facebookService.downloadFacebookVideo(url).enqueue(object :Callback<FbResponse>{
            override fun onResponse(p0: Call<FbResponse>, response: Response<FbResponse>) {
                if (response.isSuccessful) {
                    val body = response.body() as FbResponse
                    fbState.value  = LoadState.Success(body)
                } else {
                    fbState.value = LoadState.Error(response.message() + response.code())
                }
            }
            override fun onFailure(p0: Call<FbResponse>, response: Throwable) {
                fbState.value = LoadState.Error(response.message.toString())
            }
        })
        return fbState
    }

    suspend fun cdnDownloadVideo(url: String) = cdnService.downloadVideo(url).byteStream()
}