package self.paressz.core.repository.ryzendesu

<<<<<<< HEAD
=======
import android.util.Log
>>>>>>> master
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
<<<<<<< HEAD
import self.paressz.core.model.ryzendesu.RyzendesuFbResponse
import self.paressz.core.model.ryzendesu.RyzendesuIgResponse
import self.paressz.core.model.ryzendesu.RyzendesuTiktokResponse
import self.paressz.core.model.ryzendesu.RyzendesuXResponse
import self.paressz.core.model.ryzendesu.RyzendesuXResponseAlter
import self.paressz.core.network.ryzendesu.RyzendesuFacebookService
import self.paressz.core.network.ryzendesu.RyzendesuInstagramService
import self.paressz.core.network.ryzendesu.RyzendesuTiktokService
=======
import self.paressz.core.model.ryzendesu.RyzenDesuFbResponse
import self.paressz.core.model.ryzendesu.RyzenDesuIgResponse
import self.paressz.core.model.ryzendesu.RyzenDesuXResponse
import self.paressz.core.model.ryzendesu.RyzenDesuXResponseAlter
import self.paressz.core.network.ryzendesu.RyzendesuFacebookService
import self.paressz.core.network.ryzendesu.RyzendesuInstagramService
>>>>>>> master
import self.paressz.core.network.ryzendesu.RyzendesuXService
import self.paressz.core.repository.LoadState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RyzendesuDownloadRepository
@Inject constructor(
    private val ryzendesuXService: RyzendesuXService,
    private val ryzendesuInstagramService: RyzendesuInstagramService,
    private val ryzendesuFacebookService: RyzendesuFacebookService,
<<<<<<< HEAD
    private val ryzendesuTiktokService: RyzendesuTiktokService
)  {
    val xState = MutableLiveData<LoadState<RyzendesuXResponse>>()
    fun downloadXVideo(url: String) : LiveData<LoadState<RyzendesuXResponse>> {
=======
)  {
    val xState = MutableLiveData<LoadState<RyzenDesuXResponse>>()
    fun downloadXVideo(url: String) : LiveData<LoadState<RyzenDesuXResponse>> {
>>>>>>> master
        xState.value = LoadState.Loading
        ryzendesuXService.downloadXVideo(url).enqueue(object : Callback<JsonObject> {
            override fun onResponse(p0: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful && response.body() != null) {
                    val jsonResponse = response.body()!!
                    jsonResponse.let {
                        val type = it.get("type")?.asString
                        val status = it.get("status")?.asBoolean
                        val msg = it.get("msg")?.asString
                        val medias = it.get("media")?.asJsonArray
<<<<<<< HEAD
                        val mediaItem = mutableListOf<RyzendesuXResponse.Media>()
=======
                        val mediaItem = mutableListOf<RyzenDesuXResponse.Media>()
>>>>>>> master
                        if(type == "video") {
                            medias?.forEach { media ->
                                val mediaObj = media.asJsonObject
                                val url = mediaObj.get("url").asString
                                val quality = mediaObj.get("quality").asString
<<<<<<< HEAD
                                mediaItem.add(RyzendesuXResponse.Media.MultiType(url, quality))
=======
                                mediaItem.add(RyzenDesuXResponse.Media.MultiType(url, quality))
>>>>>>> master
                            }
                        } else if (type == "image") {
                            medias?.forEach { media ->
                                val imageUrl = media.asString
<<<<<<< HEAD
                                mediaItem.add(RyzendesuXResponse.Media.Image(imageUrl))
=======
                                mediaItem.add(RyzenDesuXResponse.Media.Image(imageUrl))
>>>>>>> master
                            }
                        }
                        if(status != null)
                            xState.value =
<<<<<<< HEAD
                                LoadState.Success(RyzendesuXResponse(mediaItem, type, status, msg))
=======
                                LoadState.Success(RyzenDesuXResponse(mediaItem, type, status, msg))
>>>>>>> master
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

<<<<<<< HEAD
    val igState = MutableLiveData<LoadState<RyzendesuIgResponse>>()
    fun downloadInstagramVideo(url: String) : LiveData<LoadState<RyzendesuIgResponse>>  {
        igState.value = LoadState.Loading
        ryzendesuInstagramService.donwloadInstagramVideo(url) .enqueue(object : Callback<RyzendesuIgResponse>{
            override fun onResponse(p0: Call<RyzendesuIgResponse>, response: Response<RyzendesuIgResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    igState.value = LoadState.Success(body as RyzendesuIgResponse)
=======
    val xStateAlter = MutableLiveData<LoadState<RyzenDesuXResponseAlter>>()
    fun downloadXVideoAlter(url: String) : LiveData<LoadState<RyzenDesuXResponseAlter>> {
        xStateAlter.value = LoadState.Loading
        ryzendesuXService.downloadXVideoAlter(url).enqueue(object :Callback<RyzenDesuXResponseAlter>{
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
    fun downloadInstagramVideo(url: String) : LiveData<LoadState<RyzenDesuIgResponse>>  {
        igState.value = LoadState.Loading
        ryzendesuInstagramService.donwloadInstagramVideo(url) .enqueue(object : Callback<RyzenDesuIgResponse>{
            override fun onResponse(p0: Call<RyzenDesuIgResponse>, response: Response<RyzenDesuIgResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    igState.value = LoadState.Success(body as RyzenDesuIgResponse)
>>>>>>> master
                } else {
                    igState.value = LoadState.Error(response.message() + response.code())
                }
            }

<<<<<<< HEAD
            override fun onFailure(p0: Call<RyzendesuIgResponse>, response: Throwable) {
=======
            override fun onFailure(p0: Call<RyzenDesuIgResponse>, response: Throwable) {
>>>>>>> master
                igState.value = LoadState.Error(response.message.toString())
            }
        })
        return igState
    }

<<<<<<< HEAD
    val fbState = MutableLiveData<LoadState<RyzendesuFbResponse>>()
    fun downloadFacebookVideo(url: String) : LiveData<LoadState<RyzendesuFbResponse>> {
        fbState.value = LoadState.Loading
        ryzendesuFacebookService.downloadFacebookVideo(url).enqueue(object :Callback<RyzendesuFbResponse>{
            override fun onResponse(p0: Call<RyzendesuFbResponse>, response: Response<RyzendesuFbResponse>) {
                if (response.isSuccessful) {
                    val body = response.body() as RyzendesuFbResponse
=======
    val fbState = MutableLiveData<LoadState<RyzenDesuFbResponse>>()
    fun downloadFacebookVideo(url: String) : LiveData<LoadState<RyzenDesuFbResponse>> {
        fbState.value = LoadState.Loading
        ryzendesuFacebookService.downloadFacebookVideo(url).enqueue(object :Callback<RyzenDesuFbResponse>{
            override fun onResponse(p0: Call<RyzenDesuFbResponse>, response: Response<RyzenDesuFbResponse>) {
                if (response.isSuccessful) {
                    val body = response.body() as RyzenDesuFbResponse
>>>>>>> master
                    fbState.value  = LoadState.Success(body)
                } else {
                    fbState.value = LoadState.Error(response.message() + response.code())
                }
            }
<<<<<<< HEAD
            override fun onFailure(p0: Call<RyzendesuFbResponse>, response: Throwable) {
=======
            override fun onFailure(p0: Call<RyzenDesuFbResponse>, response: Throwable) {
>>>>>>> master
                fbState.value = LoadState.Error(response.message.toString())
            }
        })
        return fbState
    }
<<<<<<< HEAD

    val tiktokState = MutableLiveData<LoadState<RyzendesuTiktokResponse>>()
    fun downloadTiktokVideo(url: String) : LiveData<LoadState<RyzendesuTiktokResponse>> {
        tiktokState.value = LoadState.Loading
        ryzendesuTiktokService.downloadTiktokVideo(url).enqueue(object :Callback<RyzendesuTiktokResponse>{
            override fun onResponse(
                p0: Call<RyzendesuTiktokResponse>,
                response: Response<RyzendesuTiktokResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.code == 0) {
                            tiktokState.value = LoadState.Success(responseBody as RyzendesuTiktokResponse)
                        } else {
                            tiktokState.value = LoadState.Error(responseBody.msg)
                        }
                    } else {
                        tiktokState.value = LoadState.Error("Response body is null")
                    }
                }
            }

            override fun onFailure(p0: Call<RyzendesuTiktokResponse>, t: Throwable) {
                tiktokState.value = LoadState.Error(t.message.toString())
            }
        })
        return tiktokState
    }
=======
>>>>>>> master
}