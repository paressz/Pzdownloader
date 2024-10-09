package self.paressz.core.repository.github

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import self.paressz.core.model.github.GithubRelease
import self.paressz.core.network.github.GithubService
import self.paressz.core.repository.LoadState
import javax.inject.Inject

class GithubRepository @Inject constructor(val githubService: GithubService) {
    private val checkUpdateState = MutableLiveData<LoadState<GithubRelease>>()
    fun checkForUpdate() : LiveData<LoadState<GithubRelease>> {
        checkUpdateState.value = LoadState.Loading
        githubService.getLatestRelease().enqueue(object : Callback<GithubRelease>{
            override fun onResponse(p0: Call<GithubRelease>, response: Response<GithubRelease>) {
                if(response.isSuccessful && response.body() != null) {
                    val data = response.body() as GithubRelease
                    checkUpdateState.value = LoadState.Success(data)
                } else {
                    checkUpdateState.value = LoadState.Error("${response.code()}: ${response.message()}")
                }
            }
            override fun onFailure(p0: Call<GithubRelease>, t: Throwable) {
                checkUpdateState.value = LoadState.Error(t.toString())
            }
        })
        return checkUpdateState
    }
}