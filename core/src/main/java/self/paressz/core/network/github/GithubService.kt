package self.paressz.core.network.github

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import self.paressz.core.BuildConfig
import self.paressz.core.model.github.GithubRelease

interface GithubService {
    @Headers(
        "Accept: application/vnd.github+json",
        "Authorization: Bearer ${BuildConfig.GITHUB_TOKEN}",
    )
    @GET("repos/paressz/Pzdownloader/releases/latest")
    fun getLatestRelease() : Call<GithubRelease>
}