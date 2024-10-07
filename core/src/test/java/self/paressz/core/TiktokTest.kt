package self.paressz.core

import org.junit.Assert
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import self.paressz.core.model.ryzendesu.RyzendesuTiktokResponse
import self.paressz.core.network.ryzendesu.RyzendesuApiClient
import self.paressz.core.repository.LoadState
import java.util.concurrent.CountDownLatch

class TiktokTest {
    val url = "https://www.tiktok.com/@thurrr___/video/7399138108488436998"

    @Test
    fun downloadTiktokVideo() {
        val service = RyzendesuApiClient.getTiktokService()
        val latch = CountDownLatch(1)
        val mockResponse = RyzendesuTiktokResponse(
            msg = "success", code = 0,
            data = RyzendesuTiktokResponse.PostData(
                videoUrl = "", title = "",
                watermarkedVideoUrl = "", cover = "",
                duration = 321, id = "7399138108488436998",
                playCount = 0, shareCount = 0,
                size = 0, watermarkedVideoSize = 0,
                author = RyzendesuTiktokResponse.TiktokAuthorData(
                    uniqueId = "", nickname = "",
                    id = "6505398567421755394", avatar = ""
                )
            )
        )
        service.downloadTiktokVideo(url).enqueue(object : Callback<RyzendesuTiktokResponse> {
            override fun onResponse(
                p0: Call<RyzendesuTiktokResponse>,
                response: Response<RyzendesuTiktokResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.code == 0) {
                            assert(responseBody.data?.id == mockResponse.data?.id)
                        } else {
                            Assert.fail("Response code is not 0")
                        }
                    } else {
                        Assert.fail("Response body is null")
                    }
                }
                latch.countDown()
            }

            override fun onFailure(p0: Call<RyzendesuTiktokResponse>, t: Throwable) {
                Assert.fail(t.message)
                latch.countDown()
            }
        })
        try {
            latch.await()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}