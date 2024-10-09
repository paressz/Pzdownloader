package self.paressz.core

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import self.paressz.core.model.ryzendesu.RyzendesuFbResponse
import self.paressz.core.network.ryzendesu.RyzendesuApiClient
import java.util.concurrent.CountDownLatch

class FbTest {
    @Test
    fun fbDonwloadTest() {
        val testUrl = "https://www.facebook.com/share/v/dPaqQ4xEWgC2w3Me/"
        val service = RyzendesuApiClient.getFacebookService()
        val title = "magnanakaw"
        val data = listOf(RyzendesuFbResponse.DataItem(thumbnail = "thumbnailUrl", resolution = "720", url = "hdUrl", shouldRender = false))
        val mockResponse = RyzendesuFbResponse(status = true, data = data)
        val mResult = service.downloadFacebookVideo(testUrl)
        val latch = CountDownLatch(1)
        mResult.enqueue(object : Callback<RyzendesuFbResponse> {
            override fun onResponse(p0: Call<RyzendesuFbResponse>, response: Response<RyzendesuFbResponse>) {
                assertEquals(mockResponse.status, response.body()?.status)
                latch.countDown()
            }

            override fun onFailure(p0: Call<RyzendesuFbResponse>, p1: Throwable) {
                Assert.fail("Request failed")
                latch.countDown()
            }
        })
        latch.await()
    }
}